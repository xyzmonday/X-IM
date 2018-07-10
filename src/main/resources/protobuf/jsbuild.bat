@echo off
set input_path=%~dp0proto\js
for /R %input_path% %%a in (*.proto) do (
   protoc -I=%input_path% --js_out=import_style=commonjs,binary:%input_path%\build %%a
)