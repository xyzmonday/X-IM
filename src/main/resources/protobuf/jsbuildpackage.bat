@echo off
set input_path=%~dp0proto\js\build
browserify %input_path%/MessageBodyExport.js > %input_path%/messagebody.js

