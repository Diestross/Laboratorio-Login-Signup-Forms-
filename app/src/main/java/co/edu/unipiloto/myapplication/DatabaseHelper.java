package co.edu.unipiloto.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userDatabase";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_USUARIO = "usuario";
    private static final String COLUMN_CORREO = "correo";
    private static final String COLUMN_DIRECCION = "direccion";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_GENERO = "genero";
    private static final String COLUMN_ROL = "rol";
    private static final String COLUMN_COORDENADAS = "coordenadas";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de usuarios
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOMBRE + " TEXT,"
                + COLUMN_USUARIO + " TEXT,"
                + COLUMN_CORREO + " TEXT,"
                + COLUMN_DIRECCION + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_GENERO + " TEXT,"
                + COLUMN_ROL + " TEXT,"
                + COLUMN_COORDENADAS + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tabla si existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Método para registrar un usuario
    public boolean registerUser(String nombre, String usuario, String correo, String direccion, String password, String genero, String rol, String coordenadas) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_USUARIO, usuario);
        values.put(COLUMN_CORREO, correo);
        values.put(COLUMN_DIRECCION, direccion);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_GENERO, genero);
        values.put(COLUMN_ROL, rol);
        values.put(COLUMN_COORDENADAS, coordenadas);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1; // Si el resultado no es -1, significa que la inserción fue exitosa
    }

    // Método para validar el login
    public boolean checkUser(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USUARIO + "=?" + " AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {usuario, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }
}

