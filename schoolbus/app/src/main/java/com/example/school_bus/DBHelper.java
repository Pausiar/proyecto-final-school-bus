public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "schoolbus.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE alumnos (" +
                "id_alumno INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "apellidos TEXT," +
                "direccion TEXT," +
                "telefono_padre TEXT," +
                "telefono_madre TEXT," +
                "curso TEXT)");

        db.execSQL("CREATE TABLE buses (" +
                "id_bus INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matricula TEXT," +
                "numero_plazas INTEGER)");

        db.execSQL("CREATE TABLE conductores (" +
                "id_conductor INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "telefono TEXT)");

        db.execSQL("CREATE TABLE rutas (" +
                "id_ruta INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre_ruta TEXT," +
                "id_bus INTEGER," +
                "id_conductor INTEGER)");

        db.execSQL("CREATE TABLE alumnos_rutas (" +
                "id_rel INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_alumno INTEGER," +
                "id_ruta INTEGER)");

        db.execSQL("CREATE TABLE asistencia (" +
                "id_asistencia INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_alumno INTEGER," +
                "fecha TEXT," +
                "subio INTEGER," +
                "bajo INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS alumnos");
        db.execSQL("DROP TABLE IF EXISTS buses");
        db.execSQL("DROP TABLE IF EXISTS conductores");
        db.execSQL("DROP TABLE IF EXISTS rutas");
        db.execSQL("DROP TABLE IF EXISTS alumnos_rutas");
        db.execSQL("DROP TABLE IF EXISTS asistencia");
        onCreate(db);
    }
}
