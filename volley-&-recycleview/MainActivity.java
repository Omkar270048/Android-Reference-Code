public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private ArrayList<Model> modelArrayList = new ArrayList<Model>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle_view);

//      requesting json array request
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
//        return "progress finished";
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new Adapter(modelArrayList, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
