package fr.hibon.modepassesecurest.compte.bdd;

/*
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
*/
import android.support.v7.app.AppCompatActivity;
/*
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;

import fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee;
import fr.hibon.modepassesecurest.ihm.compte.GestionIHM;

import static fr.hibon.modepassesecurest.compte.bdd.table.Table_Donnee.DONNEE_TABLE_NOM;
*/


/**
 * Classe pour l'export en fichier csv
 * <BR>!! EN COURS DE DEVELOPPEMENT
 */

public class Export extends AppCompatActivity {



/*
        Context context = this ;
        File dbFile= GestionBaseLocale.getDatabaseNom();
            // getDatabasePath(GestionBaseLocale.getDatabaseNom()) ;
            //context.getDatabasePath(GestionBaseLocale.getDatabaseNom())
            // GestionBaseLocale.getDatabaseNom() +".sqlite");
        GestionBaseLocale dbhelper = new GestionBaseLocale(context);
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "csvname.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            String req = "SELECT * FROM " + DONNEE_TABLE_NOM ;
            Cursor curCSV = db.rawQuery(req,null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
                csvWrite.writeNext(arrStr);
            }

            csvWrite.close();
            curCSV.close();
        }
        catch(Exception sqlEx)
        {
            GestionIHM.pop2Choix(this, "Probleme", sqlEx.getMessage());

        }

*/



}
