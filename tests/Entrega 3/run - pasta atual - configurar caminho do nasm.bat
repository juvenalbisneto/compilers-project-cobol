@echo off
cd C:\NASM
cls
echo # Inicializando... #
set batchPath=%~dp0
cls
echo.
echo		Execucao do Programa - Projeto de Compiladores - COBOL
echo				by Juvenal Bisneto
echo.
echo.
echo # GERANDO OBJETO #
nasm -f win32 "%batchPath%program.asm" -o "%batchPath%program.o"
echo.
echo # GERANDO EXECUTAVEL #
gcc "%batchPath%program.o" -o "%batchPath%program.exe"
echo.
echo # EXECUCAO #
"%batchPath%program.exe"
echo # FIM #
del "%batchPath%program.o"
del "%batchPath%program.exe"
set batchPath=
pause