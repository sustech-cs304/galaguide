% Start Frontend %
start "galaguide" cmd /k "cd .\galaguide\ & npm run electron:serve"

@REM Start Backend
cd .\pseudo\
python app.py

@REM cd .\backend
@REM ./gradlew.bat runFatJar