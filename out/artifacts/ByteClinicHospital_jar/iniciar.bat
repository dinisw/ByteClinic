@echo off
:: Muda para a pasta onde o ficheiro .bat está
cd /d "%~dp0"

:: Abre o PowerShell, configura o UTF-8 e executa o JAR protegendo os argumentos
start powershell -NoExit -ExecutionPolicy Bypass -Command "[Console]::OutputEncoding = [System.Text.Encoding]::UTF8; java '-Dfile.encoding=UTF-8' -jar 'ByteClinicHospital.jar'"