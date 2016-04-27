package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RuleBook extends Activity {

    File pdfFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pdfFile = new File("/storage/emulated/0/Android/data/edu.uco.rconyac1.magiccompanion/files/mtgRuleBook.pdf");
        if(pdfFile.exists())
        {
            openFile();
        }
        else
        {
          copyAssets();
        }
        finish();
    }

    private void copyAssets() {
        AssetManager assetManager = getAssets();

            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open("mtgRuleBook.pdf");
                File outFile = new File(getExternalFilesDir(null), "mtgRuleBook.pdf");
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + "mtgRuleBook.pdf", e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {

                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {

                    }
                }
            }

    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    public void openFile()
    {
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try
        {
            startActivity(pdfIntent);
        }
        catch(ActivityNotFoundException e)
        {
            Toast.makeText(RuleBook.this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        Intent intent;
        switch(id) {
            case R.id.life_counter:
                intent = new Intent(RuleBook.this,LifeCounterMain.class);
                startActivity(intent);
                return true;
            case R.id.dice_roller:
                intent = new Intent(RuleBook.this, DiceRollerActivity.class);
                startActivity(intent);
                return true;
            case R.id.rulebook:
                intent = new Intent(RuleBook.this,RuleBook.class);
                startActivity(intent);
                return true;
            case R.id.deck_builder:
                intent = new Intent(RuleBook.this, DeckBuilderActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
