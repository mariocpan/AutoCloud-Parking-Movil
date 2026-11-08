package co.sena.autocloudparking;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
public class Exportar {
 private static OutputStream abrir(Context ctx, String nombre, String tipoArchivo) throws  Exception {
     ContentValues datos = new ContentValues();
     datos.put(MediaStore.Downloads.DISPLAY_NAME, nombre);
     datos.put(MediaStore.Downloads.MIME_TYPE, tipoArchivo);
     datos.put(MediaStore.Downloads. RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
     Uri uri = ctx.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, datos);
     return ctx.getContentResolver().openOutputStream(uri);
 }
        public  static boolean  pdf(Context ctx, String nombre, List<String>lineas) {
        try {
            PdfDocument doc =  new PdfDocument();
            Paint pincel = new Paint();
            pincel.setTextSize(12);
            int y = 50;
            int numero = 1;
            PdfDocument.Page pagina = doc.startPage(new PdfDocument.PageInfo.Builder(595,842,numero).create());

            for(String linea : lineas) {
                if(y> 800) {
                    doc.finishPage(pagina);
                    numero++;
                    pagina = doc.startPage(new PdfDocument.PageInfo.Builder(595, 842, numero).create());

                }
                pagina.getCanvas().drawText(linea, 40, y, pincel );
                y += 20;

            }
            doc.finishPage(pagina);

            OutputStream salida = abrir(ctx, nombre, "aplication/pdf");
            doc.writeTo(salida);
            salida.close();
            doc.close();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
    public  static  boolean csv(Context ctx, String nombre, List<String[]> filas) {
     try {
         StringBuilder texto = new StringBuilder("\uFEFFPlaca;Tipo;Ingreso;Salida;Valor\n");
         for(String[] f: filas) {
            texto.append(f[0]).append(";").append(f[1]).append(";").append(f[2])
                    .append(";").append(f[3]).append(";").append(f[4]).append("\n");
         }
         OutputStream salida = abrir(ctx, nombre,"text/csv");
         salida.write(texto.toString().getBytes(StandardCharsets.UTF_8));
         salida.close();
         return  true;
     } catch (Exception e) {
         return false;
     }
    }
}
