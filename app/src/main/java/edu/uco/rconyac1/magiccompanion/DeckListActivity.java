package edu.uco.rconyac1.magiccompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ryan on 11/6/2015.
 */
public class DeckListActivity extends Activity {

    private Button create, search;
    private EditText deckNameField, searchField;
    private String searchString, deckNameString;
    private ArrayList<Deck> loadedDecks;
    private ListView listView;
    private String[] deckFiles;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deck_list);
        create = (Button) findViewById(R.id.deckListCreateButton);
        //search = (Button) findViewById(R.id.deckListSearchButton);
        deckNameField = (EditText) findViewById(R.id.deckListName);
        //searchField = (EditText) findViewById(R.id.deckListSearchField);
        //searchString = searchField.getText().toString();
        loadedDecks = new ArrayList<>();
        loadDecks();
        listView = (ListView) findViewById(R.id.deckListListView);
        adapter = new ArrayAdapter(this, R.layout.card_item_list, deckFiles);
        listView.setAdapter(adapter);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deckNameString = deckNameField.getText().toString();
                Intent i = new Intent(DeckListActivity.this, DeckBuilderActivity.class);
                i.putExtra("deckName", deckNameString);
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                @Override
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Log.d("MTG", "the long click activated");
                                                    Bundle bundle = new Bundle();
                                                    bundle.putInt("position", position);
                                                    DeckListDialog dialog = new DeckListDialog();
                                                    dialog.setArguments(bundle);
                                                    dialog.show(getFragmentManager(), "delete_confirm");
                                                    return true;
                                                }
                                            }
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(DeckListActivity.this, DeckBuilderActivity.class);
                i.putExtra("deckName", loadedDecks.get(position).getDeckName());
                i.putExtra("deck", loadedDecks.get(position));
                startActivity(i);
            }
        });
        //load the decks from the file directory

    }

    public void loadDecks() {
        //load from the internal file system
        deckFiles = fileList();
        if (deckFiles.length != 0) {
            for (int i = 0; i < deckFiles.length; i++) {
                try {
                    FileInputStream file = openFileInput(deckFiles[i]);
                    ObjectInput input = new ObjectInputStream(file);
                    Deck temp = (Deck) input.readObject();
                    loadedDecks.add(temp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (StreamCorruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteDeck(int position) {
        File mydirectory = getFilesDir();
        File file = new File(mydirectory, loadedDecks.get(position).getDeckName());
        file.delete();
        adapter.clear();
        adapter.add(deckFiles);
        adapter.notifyDataSetChanged();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        String[] temp = fileList();
//        if(deckFiles.length != 0) {
//            updateList();
//        }
//
//    }
//
//    public void updateList() {
//        loadedDecks = null;
//        loadDecks();
//        adapter.clear();
//        deckFiles = fileList();
//        adapter.addAll(deckFiles);
//        adapter.notifyDataSetChanged();
//    }
}
