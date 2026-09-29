package com.example.notasdocente.dao;

public class CodigoSQLBackUp {
    public static String sqlBackup =
            "CREATE TABLE [Universidades] (\n" +
                    "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,\n" +
                    "  [nombre] VARCHAR NOT NULL,\n" +
                    "  [www] VARCHAR\n" +
                    ");\n" +
                    "CREATE TABLE [Programas] (\n" +
                    "  [id] INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,\n" +
                    "  [nombre] VARCHAR NOT NULL,\n" +
                    "  [director] VARCHAR DEFAULT 'John Carlos Arrieta Arrieta',\n" +
                    "  [email] VARCHAR,\n" +
                    "  [universidades_id] INTEGER NOT NULL,\n" +
                    "  FOREIGN KEY (universidades_id) REFERENCES Universidades (id)\n" +
                    ");\n" +
                    "CREATE TABLE [Alumnos] (\n" +
                    "  [codigo] VARCHAR PRIMARY KEY NOT NULL UNIQUE,\n" +
                    "  [nombre] VARCHAR NOT NULL,\n" +
                    "  [apellidos] VARCHAR NOT NULL,\n" +
                    "  [email] VARCHAR,\n" +
                    "  [telefono] VARCHAR,\n" +
                    "  [programas_id] INTEGER NOT NULL,\n" +
                    "  FOREIGN KEY (programas_id) REFERENCES Programas (id)\n" +
                    ");\n" +
                    "CREATE TABLE [Asignaturas] (\n" +
                    "  [codigo] VARCHAR PRIMARY KEY NOT NULL UNIQUE,\n" +
                    "  [nombre] VARCHAR NOT NULL,\n" +
                    "  [programas_id] INTEGER,\n" +
                    "  FOREIGN KEY (programas_id) REFERENCES Programas (id)\n" +
                    ");\n" +
                    "CREATE TABLE [Calificaciones] (\n" +
                    "  [alumnos_id] VARCHAR NOT NULL,\n" +
                    "  [asignaturas_id] VARCHAR NOT NULL,\n" +
                    "  [nota1] FLOAT DEFAULT 0.0,\n" +
                    "  [peso1] FLOAT DEFAULT 0.30,\n" +
                    "  [nota2] FLOAT DEFAULT 0.0,\n" +
                    "  [peso2] FLOAT DEFAULT 0.30,\n" +
                    "  [nota3] FLOAT DEFAULT 0.0,\n" +
                    "  [peso3] FLOAT DEFAULT 0.40,\n" +
                    "  PRIMARY KEY ([alumnos_id], [asignaturas_id]),\n" +
                    "  FOREIGN KEY (alumnos_id) REFERENCES Alumnos (codigo),\n" +
                    "  FOREIGN KEY (asignaturas_id) REFERENCES Asignaturas (codigo)\n" +
                    ");";
}