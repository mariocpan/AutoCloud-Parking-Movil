package co.sena.autocloudparking;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;
public class BaseDatos extends SQLiteOpenHelper {
    private static BaseDatos instancia;
    public static synchronized BaseDatos obtener(Context contexto) {
        if(instancia== null) {
            instancia = new BaseDatos(contexto.getApplicationContext());
        }
        return instancia;
    }
    public BaseDatos(Context contexto) {
        super(contexto, "autocloud.db", null, 3);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS vehiculo (id  INTEGER PRIMARY KEY AUTOINCREMENT, placa TEXT, tipo TEXT, ingreso TEXT,salida TEXT,valor INTEGER)");
        db.execSQL("CREATE TABLE  IF NOT EXISTS usuario(usuario TEXT PRIMARY KEY,sal  TEXT, contraseña TEXT )");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int anterior, int nueva) {
        db.execSQL("DROP TABLE IF EXISTS vehiculo");
        onCreate(db);

    }
    public boolean registrarUsuario(String usuario, String contraseña ) {
        String sal = Seguridad.nuevaSal();
        ContentValues datos = new ContentValues();
        datos.put("usuario", usuario);
        datos.put("sal", sal);
        datos.put("Contraseña", Seguridad.hash(contraseña,sal));
        return getWritableDatabase().insert("usuario",null, datos) != -1;
    }

    public boolean validarUsuario(String usuario, String contraseña) {
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT sal, contraseña FROM usuario WHERE usuario= ?", new String[]{usuario});
        boolean valido = false;
        if (c.moveToFirst()) {
            valido = Seguridad.hash(contraseña, c.getString(0)).equals(c.getString(1));
        }
        c.close();
        return valido;
    }


    public boolean guardar(String placa,String tipo,String ingreso) {
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT 1 FROM vehiculo WHERE placa =? AND salida IS null", new String[]{placa});
        boolean dentro = c.moveToFirst();
        c.close();
        if (dentro) return false;
        ContentValues datos = new ContentValues();
        datos.put("placa", placa);
        datos.put("tipo", tipo);
        datos.put("ingreso", ingreso );
        return getWritableDatabase().insert("vehiculo",null, datos) !=  -1;

    }
    public String[] buscar(String placa) {
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT tipo, ingreso FROM vehiculo WHERE placa = ?AND salida IS NULL", new String[]{placa});
        String[] ficha = null;
        if (c.moveToFirst()) {
            ficha = new String[]{c.getString(0), c.getString(1)};
        }
        c.close();
        return ficha;
    }
    public boolean cobrar(String placa, String salida, long valor) {
        ContentValues datos = new ContentValues();
        datos.put("salida", salida);
        datos.put("valor", valor);
        return getWritableDatabase().update("vehiculo", datos, "placa =? AND salida IS NULL", new String[]{placa}) > 0;
    }
     public String siguenteCodigoBici() {
         Cursor c = getReadableDatabase().rawQuery(
                 "SELECT COUNT(*) FROM vehiculo WHERE tipo = 'Bicicleta'", null);
         c.moveToFirst();
         int numero = c.getInt(0) + 1;
         c.close();
         return String.format("BICI%03d", numero);
     }
     public String dentro() {
        Cursor c = getReadableDatabase().rawQuery(
                 "SELECT placa, tipo, ingreso FROM vehiculo  WHERE salida  IS NULL ORDER  BY ID DESC", null);
        StringBuilder texto = new StringBuilder();
     while (c.moveToNext()) {
         texto.append(c.getString(0)).append("-").append(c.getString(1))
                 .append("\n Ingreso: ").append(c.getString(2)).append("\n\n");
     }
     int total = c.getCount();
     c.close();
     if(total == 0 ) return "No hay vehículos en el parqueadero";
     return "Dentro del parqueadero:" + total +"\n\n"+ texto;
     }
    public List<String[]> cobrados() {
        List<String[]> lista = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(
         "SELECT placa,tipo, ingreso, salida, valor FROM Vehiculo WHERE salida  IS NOT NULL ORDER BY id DESC", null);
        while (c.moveToNext()) {
            lista.add(new String[]{c.getString(0),c.getString(1), c.getString(2),
                c.getString(3), String.valueOf(c.getString(4))});

        }
        c.close();
        return lista;

    }
    public String listar() {
        Cursor c = getReadableDatabase().rawQuery("SELECT  placa, tipo, ingreso FROM vehiculo ORDER BY rowid DESC", null);
        StringBuilder texto = new StringBuilder();
        while (c.moveToNext()) {
            texto.append(c.getString(0)).append("-").append(c. getString(1))
                    .append("\n Ingreso: ").append(c.getString(2)).append("\n\n");

        }
        int total=c.getCount();
        c. close();
        if (total == 0) return "no hay Vehiculos registrados";
        return "Total:" + total + "vehiculos\n\n" + texto;
    }
}
