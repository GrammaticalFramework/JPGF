@echo off

rem ##########################################################################
rem # Copyright 2002-2010, LAMP/EPFL
rem #
rem # This is free software; see the distribution for copying conditions.
rem # There is NO warranty; not even for MERCHANTABILITY or FITNESS FOR A
rem # PARTICULAR PURPOSE.
rem ##########################################################################

rem We adopt the following conventions:
rem - System/user environment variables start with a letter
rem - Local batch variables start with an underscore ('_')

if "%OS%"=="Windows_NT" (
  @setlocal
  call :set_home
  set _ARGS=%*
) else (
  set _SCALA_HOME=%SCALA_HOME%
  rem The following line tests SCALA_HOME instead of _SCALA_HOME, because
  rem the above change to _SCALA_HOME is not visible within this block.
  if "%SCALA_HOME%"=="" goto error1
  call :set_args
)

rem We use the value of the JAVACMD environment variable if defined
set _JAVACMD=%JAVACMD%

if "%_JAVACMD%"=="" (
  if not "%JAVA_HOME%"=="" (
    if exist "%JAVA_HOME%\bin\java.exe" set _JAVACMD=%JAVA_HOME%\bin\java.exe
  )
)

if "%_JAVACMD%"=="" set _JAVACMD=java

rem We use the value of the JAVA_OPTS environment variable if defined
set _JAVA_OPTS=%JAVA_OPTS%
if "%_JAVA_OPTS%"=="" set _JAVA_OPTS=-Xmx256M -Xms16M

set _TOOL_CLASSPATH=%_SCALA_HOME%\misc\sbaz\scala-bazaars.jar;%_SCALA_HOME%\misc\sbaz\sbaz.jar;%_SCALA_HOME%\misc\sbaz\scala-library.jar
if "%_TOOL_CLASSPATH%"=="" (
  for %%f in ("%_SCALA_HOME%\lib\*") do call :add_cpath "%%f"
  if "%OS%"=="Windows_NT" (
    for /d %%f in ("%_SCALA_HOME%\lib\*") do call :add_cpath "%%f"
  )
)

set _PROPS=-Dscala.home="%_SCALA_HOME%" -Denv.classpath="%CLASSPATH%" -Denv.emacs="%EMACS%" 

rem echo "%_JAVACMD%" %_JAVA_OPTS% %_PROPS% -cp "%_TOOL_CLASSPATH%" sbaz.clui.CommandLine  %_ARGS%
"%_JAVACMD%" %_JAVA_OPTS% %_PROPS% -cp "%_TOOL_CLASSPATH%" sbaz.clui.CommandLine  %_ARGS%

rem Finsish install of sbaz dependencies now that JVM isn't using them
rem THIS IS SBAZ SPECIFIC LOGIC
if exist "%_SCALA_HOME%\misc\sbaz\scala-bazaars.jar.staged" (
  move /Y "%_SCALA_HOME%\misc\sbaz\scala-bazaars.jar.staged" "%_SCALA_HOME%\misc\sbaz\scala-bazaars.jar" > nul
)
if exist "%_SCALA_HOME%\misc\sbaz\sbaz.jar.staged" (
  move /Y "%_SCALA_HOME%\misc\sbaz\sbaz.jar.staged" "%_SCALA_HOME%\misc\sbaz\sbaz.jar" > nul
)
if exist "%_SCALA_HOME%\misc\sbaz\scala-library.jar.staged" (
  move /Y "%_SCALA_HOME%\misc\sbaz\scala-library.jar.staged" "%_SCALA_HOME%\misc\sbaz\scala-library.jar" > nul
)
rem Transitional support for pre sbaz 2.0
if exist "%_SCALA_HOME%\misc\sbaz\scala-library.jar.tmp" (
  move /Y "%_SCALA_HOME%\misc\sbaz\scala-library.jar.tmp" "%_SCALA_HOME%\misc\sbaz\scala-library.jar" > nul
)
 
goto end

rem ##########################################################################
rem # subroutines

:add_cpath
  if "%_TOOL_CLASSPATH%"=="" (
    set _TOOL_CLASSPATH=%~1
  ) else (
    set _TOOL_CLASSPATH=%_TOOL_CLASSPATH%;%~1
  )
goto :eof

rem Variable "%~dps0" works on WinXP SP2 or newer
rem (see http://support.microsoft.com/?kbid=833431)
rem set _SCALA_HOME=%~dps0..
:set_home
  set _BIN_DIR=
  for %%i in (%~sf0) do set _BIN_DIR=%_BIN_DIR%%%~dpsi
  set _SCALA_HOME=%_BIN_DIR%..
goto :eof

:set_args
  set _ARGS=
  :loop
  rem Argument %1 may contain quotes so we use parentheses here
  if (%1)==() goto :eof
  set _ARGS=%_ARGS% %1
  shift
  goto loop

rem ##########################################################################
rem # errors

:error1
echo ERROR: environment variable SCALA_HOME is undefined. It should point to your installation directory.
goto end

:end
if "%OS%"=="Windows_NT" @endlocal
