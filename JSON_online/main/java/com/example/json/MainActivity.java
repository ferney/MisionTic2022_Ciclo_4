package com.example.json;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.*;
import android.util.*;
import android.widget.*;
import org.json.*;
import java.util.*;


public class MainActivity extends AppCompatActivity {


    private ProgressDialog pDialog;
    private ListView lv;

    // URL para obtener el JSON de los contactos
    private static String url = "https://jsonplaceholder.typicode.com/users";
/*
* Los usos de un Hashmap son un Índice de HashValues para encontrar los valores mucho más rápido.
* contendrá en el array datos obtenidos de un servicio web que sirve datos json.
* */
    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();
    }

    /**
     * AsyncTask nos permite usar facilmente el thread UI (hilo de la interfaz de usuario).
     * Con esta clase podemos ejecutar operaciones en segundo plano y mostrar los resultados
     * en el hilo UI sin tener que manipular hilos en el SO de forma manual.
     * AsyncTask es ideal para realizar operaciones cortas (de unos pocos segundos).
     * Si necesitas tener hilos durante largos periodos de tiempo en ejecución,
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        /*
        * onPreExecute(): En este método tenemos que realizar los trabajos previos a la ejcución de la tarea.
        * Se utiliza normalmente para configurar la tarea y para mostrar en el la interfaz de usuario que empieza la tarea.
        * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostrar el diálogo de progreso
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("espere por favor...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Hacer una petición a la url y obtener respuesta
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Obtención del nodo JSON Array
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // bucle a través de todos los contactos
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String name = c.getString("name");
                        String email = c.getString("email");
                        // El nodo phone es un objeto JSON
                        //JSONObject phone = c.getJSONObject("phone");
                        String mobile = c.getString("phone");

                        // mapa hash tmp para un solo contacto
                        HashMap<String, String> contact = new HashMap<>();

                        // añadiendo cada nodo hijo a HashMap key => value
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // añadir un contacto a la lista de contactos
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "error en json: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "error en el servidor!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Abandonar el diálogo de progreso
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Actualización de los datos JSON parseados en el ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this,
                    contactList,
                    R.layout.list_item,
                    new String[]{"name", "email", "mobile"},
                    new int[]{R.id.name, R.id.email, R.id.mobile});

            lv.setAdapter(adapter);
        }

    }
}