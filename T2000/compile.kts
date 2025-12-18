#!/usr/bin/env kotlin

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.io.path.exists
import kotlin.io.path.isDirectory
import kotlin.io.path.nameWithoutExtension

println("Starting compilation...")

val whichCommand: String = if (System.getProperty("os.name").lowercase().contains("windows")) {
    "where"
} else {
    "which"
}

val srcDir: Path = Paths.get("src")
val outDir: Path = srcDir.resolve("out")
val auxDir: Path = srcDir.resolve("aux")

val texFile: Path = srcDir.resolve("dokumentation.tex")
val outFile: Path = outDir.resolve("dokumentation.pdf")
val desFile: Path = Paths.get("bachelorarbeit_${getCurrentTime()}.pdf")
val outputFormat: OutputFormat = OutputFormat.PDF

enum class OutputFormat(val formatName: String) {
    PDF("pdf"), PS("ps"), DVI("dvi")
}

fun compile(){
    val checkLatexmk: Process = ProcessBuilder(whichCommand, "latexmk")
        .redirectErrorStream(true)
        .start()
    if (checkLatexmk.waitFor() != 0) {
        error("latexmk is not installed or not in PATH")
    }

    if (!srcDir.exists() || !srcDir.isDirectory()) {
        error("src directory does not exist")
    }

    val command = listOf(
        "latexmk",
        "-file-line-error",
        "-interaction=nonstopmode",
        "-synctex=1",
        "-output-format=${outputFormat.formatName}",
        "-output-directory=./${outDir.fileName}",
        "-aux-directory=./${auxDir.fileName}",
        "-lualatex",
        texFile.fileName.toString(),
    )

    val process: Process = ProcessBuilder(command)
        .directory(srcDir.toFile())
        .inheritIO()
        .start()

    val exitCode = process.waitFor()
    if (exitCode != 0) {
        error("latexmk failed with exit code $exitCode")
    }

    if (texFile.exists()) {
        Files.copy(
            outFile,
            desFile,
            StandardCopyOption.REPLACE_EXISTING
        )
        println()
        println("**********************************************************")
        println("Compilation successful!")
        println("PDF file successfully created: ${desFile.toAbsolutePath()}")
        println("**********************************************************")
        println()
    } else {
        error("Output PDF file not found: ${texFile.toAbsolutePath()}")
    }
}

fun getCurrentTime(): String {
    val formatter = DateTimeFormatter.ofPattern("MMdd_HHmmss")
    return LocalDateTime.now().format(formatter)
}

fun linearizePDF() {
    val checkQpdf: Process = ProcessBuilder(whichCommand, "qpdf")
        .redirectErrorStream(true)
        .start()
    if (checkQpdf.waitFor() != 0) {
        println("qpdf is not installed or not in PATH")
        return
    }
    val qpdfFile: Path = Paths.get(desFile.nameWithoutExtension + "_linearized.pdf")
    println("Linearizing PDF file...")
    ProcessBuilder(
        "qpdf",
        "--linearize",
        desFile.toString(),
        qpdfFile.toString(),
    )
        .inheritIO()
        .start()
        .waitFor()
    println(qpdfFile.toAbsolutePath())

}

compile()
linearizePDF()