@echo off
set input_path=%~dp0proto\java
for /R %input_path% %%a in (*.proto) do (
   protoc -I=%input_path%  --java_out=%input_path%\build %%a
)
