% Start Frontend %
start "galaguide" cmd /k "cd .\galaguide\ & npm run electron:serve"

% Start Backend	%
% cd .\pseudo\ %
% python app.py %

cd .\backend
./gradlew.bat runFatJar