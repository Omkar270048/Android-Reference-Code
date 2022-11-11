<h1>Volley and Recycleview</h1><br>
<h2>dependencies for volly</h2><br>
implementation 'com.android.volley:volley:1.1.0'

<h2>User Permission</h2><br>
&lt;uses-permission android:name=&quot;android.permission.INTERNET&quot;/&gt;

<br>
<h3> adding recycle view to layout</h3>
```
        <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/recycle_view"/>
       
        <androidx.recyclerview.widget.RecyclerView/>
``` ```
<br>
<h3> Customise recycle view in new layout file (Example: item_design.xml, cardview.xml)</h3>
```
<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:elevation="5dp"
    android:layout_margin="5dp"
    android:layout_marginBottom="10dp"
    android:background="@color/teal_200">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="id"
            android:textSize="24dp"
            android:fontFamily="sans-serif-black"
            android:id="@+id/card_id"
            android:layout_marginBottom="5dp"/>

        <TextView
            android:id="@+id/card_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="5dp"
            android:fontFamily="sans-serif-medium"
            android:text="Title"
            android:textSize="24dp" />

        <TextView
            android:id="@+id/card_body"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="5dp"
            android:fontFamily="sans-serif-light"
            android:text="body"
            android:textSize="24dp" />

    </LinearLayout>
</androidx.cardview.widget.CardView>
```

<br>
<h3> Creating Model.java file to populate our Arraylist (for this program array list is present in MainActivity.java</h3>
```
public class Model {
    private String id, title, body;

    public Model(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }

}
```

<br>
<h3> Creating Custom addapter for recycle view</h3>
```
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<Model> modelArrayList;
    private Context context;

    public Adapter(ArrayList<Model> modelArrayList, Context context) {
        this.modelArrayList = modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_design, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Model model = modelArrayList.get(position);
    holder.id.setText(model.getId());
    holder.title.setText(model.getTitle());
    holder.body.setText(model.getBody());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id, title, body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.card_id);
            title = itemView.findViewById(R.id.card_title);
            body = itemView.findViewById(R.id.card_body);

        }
    }
}

```

<br>
<h3> Calling api</h3>
```
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<Model> modelArrayList = new ArrayList<Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/posts", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i=0; i<response.length(); i++) {
                            try {
                                JSONObject responseObject = response.getJSONObject(i);
                                String id = responseObject.getString("id");
                                String title = responseObject.getString("title");
                                String body = responseObject.getString("body");
                                Model model = new Model(id, title, body);
                                modelArrayList.add(model);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("Catch Error", String.valueOf(e));
                            }
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Myapp", String.valueOf(error));
            }
        });
        requestQueue.add(jsonObjectRequest);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new Adapter(modelArrayList, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
```
