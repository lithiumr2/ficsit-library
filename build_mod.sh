#!/bin/bash
set -e

echo "[1/4] Compilando código Java con Gradle..."
gradle clean jar

JAR_PATH="build/libs/ficsit-library.jar"
DEX_DIR="build/dex_output"

echo "[2/4] Convirtiendo bytecode a Dalvik (.dex) con d8..."
mkdir -p $DEX_DIR
d8 $JAR_PATH --min-api 24 --output $DEX_DIR/

echo "[3/4] Inyectando classes.dex dentro del archivo JAR..."
cd $DEX_DIR
zip -u ../../$JAR_PATH classes.dex
cd ../../

echo "[4/4] Exportando mod a Descargas..."
cp $JAR_PATH /sdcard/Download/ficsit-library.jar

echo "¡Listo! Mod actualizado en Descargas."
