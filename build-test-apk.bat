@echo off
setlocal EnableExtensions

set "GRADLE_VERSION=9.3.1"
set "GRADLE_HOME=%USERPROFILE%\.gradle\recomeca\gradle-%GRADLE_VERSION%"
set "GRADLE_ZIP=%TEMP%\gradle-%GRADLE_VERSION%-bin.zip"

if not defined JAVA_HOME if exist "%ProgramFiles%\Android\Android Studio\jbr\bin\java.exe" set "JAVA_HOME=%ProgramFiles%\Android\Android Studio\jbr"
if defined JAVA_HOME set "PATH=%JAVA_HOME%\bin;%PATH%"
where java >nul 2>&1
if errorlevel 1 (
  echo [ERRO] JDK nao encontrado. Instale JDK 21 ou configure JAVA_HOME.
  pause
  exit /b 1
)
where keytool >nul 2>&1
if errorlevel 1 (
  echo [ERRO] keytool nao encontrado. Verifique sua instalacao do JDK 21.
  pause
  exit /b 1
)

if not defined ANDROID_HOME if exist "%LOCALAPPDATA%\Android\Sdk" set "ANDROID_HOME=%LOCALAPPDATA%\Android\Sdk"
if not defined ANDROID_SDK_ROOT if defined ANDROID_HOME set "ANDROID_SDK_ROOT=%ANDROID_HOME%"

if not defined ANDROID_HOME (
  echo [ERRO] Android SDK nao encontrado.
  echo Instale o Android Studio e o Android SDK 36.1 pelo SDK Manager.
  pause
  exit /b 1
)

if not exist "%ANDROID_HOME%\platforms\android-36.1\android.jar" (
  echo [ERRO] Android SDK Platform 36.1 nao instalado.
  echo Android Studio ^> Tools ^> SDK Manager ^> SDK Platforms ^> Android 16.0 ^> Android SDK Platform 36.1.
  pause
  exit /b 1
)

if not exist "%ANDROID_HOME%\build-tools\36.0.0\aapt.exe" (
  echo [ERRO] Android Build Tools 36.0.0 nao instalado.
  echo Instale pelo SDK Manager ^> SDK Tools ^> Android SDK Build-Tools 36.0.0.
  pause
  exit /b 1
)

if not exist "%GRADLE_HOME%\bin\gradle.bat" (
  echo Baixando Gradle %GRADLE_VERSION% pela distribuicao oficial...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $zip='%GRADLE_ZIP%'; Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip' -OutFile $zip; $dest='%USERPROFILE%\.gradle\recomeca'; New-Item -ItemType Directory -Force -Path $dest ^| Out-Null; Expand-Archive -LiteralPath $zip -DestinationPath $dest -Force; Remove-Item $zip -Force"
  if errorlevel 1 (
    echo [ERRO] Nao foi possivel baixar o Gradle. Verifique sua conexao.
    pause
    exit /b 1
  )
)

if not exist "debug.keystore" (
  echo Criando chave temporaria de depuracao...
  keytool -genkeypair -v -keystore debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000 -dname "CN=Android Debug,O=Android,C=US"
  if errorlevel 1 exit /b 1
)

>local.properties echo sdk.dir=%ANDROID_HOME:\=/%

echo.
echo Executando testes e compilando APK de demonstracao...
call "%GRADLE_HOME%\bin\gradle.bat" testDebugUnitTest assembleDebug --no-daemon
if errorlevel 1 (
  echo.
  echo [ERRO] A compilacao falhou. Copie o erro acima para o suporte.
  pause
  exit /b 1
)

echo.
echo [SUCESSO] APK gerado em:
echo app\build\outputs\apk\debug\app-debug.apk
pause
