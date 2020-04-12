package fonseca.emmanuel.eventostform;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "events.txt";
    private static String ptext;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text_orientation);
        onChangedOrientation();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.close_dialog)
                .setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ptext = text.getText().toString();
                        text.setText(getString(R.string.closeApp,ptext, new Date()));
                        save();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ptext = text.getText().toString();
                        text.setText(getString(R.string.noshutApp, ptext));
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onPause() {
        ptext = text.getText().toString();
        text.setText(getString(R.string.onPause,ptext));
        super.onPause();
    }

    @Override
    protected void onResume() {
        ptext = text.getText().toString();
        text.setText(getString(R.string.onResume,ptext));
        super.onResume();
    }

    @Override
    protected void onStop() {
        ptext = text.getText().toString();
        text.setText(getString(R.string.onResume,ptext));
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ptext = text.getText().toString();
        text.setText(getString(R.string.onResume,ptext));
        super.onDestroy();
    }

    public void save() {
        FileOutputStream fos = null;
        try {
            File file = new File(getFilesDir().getParent() + File.separator + FILE_NAME);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            fos.write(text.getText().toString().getBytes());
            Toast.makeText(this, "Guardado en " + getFilesDir().getParent() + File.separator + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void onChangedOrientation() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            ptext = text.getText().toString();
            text.setText(ptext + "\nPORTRAIT");
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ptext = text.getText().toString();
            text.setText(ptext + "\nLANDSCAPE");
        }
    }
}
