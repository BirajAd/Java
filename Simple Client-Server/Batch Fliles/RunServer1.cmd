@echo off
REM =========================================
REM Change the next line to match your system
REM =========================================
cd /d "C:\Users\bradenf\eclipse-workspace-spring18\Simple Client-Server\bin"
java -cp . edu.mnsu.braden.clientserver.echo.EchoServer1
echo Press any key to close this window.
pause > null
exit