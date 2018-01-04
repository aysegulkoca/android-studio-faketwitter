package socialnetwork.sosyalmedya;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener  {

    FirebaseAuth myAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText editTextUserName;
    EditText postText;
    List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("posts");


        postText = (EditText) findViewById(R.id.post);
        editTextUserName= (EditText) findViewById(R.id.userName);

        Button postButton = (Button) findViewById(R.id.postButton);
        postButton.setOnClickListener(this);

        postList = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.clear();
                for(DataSnapshot snap: dataSnapshot.getChildren()){
                    Log.d("FİREBASE", snap.child("author").getValue(String.class));

                    Post post = new Post(snap.child("post").getValue().toString(),
                            snap.child("author").getValue().toString());

                    postList.add(post);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ListView listViewPosts = (ListView) findViewById(R.id.listViewPost);

        CustomAdapter adapter = new CustomAdapter(this,postList);
        listViewPosts.setAdapter(adapter);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_homepage) {
            Intent intent = new Intent(HomePageActivity.this, HomePageActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profil) {
            Intent intent = new Intent(HomePageActivity.this, ProfilActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void addPost(){
        DatabaseReference databaseReferenceUsers = FirebaseDatabase.getInstance()
                .getReference("users");
        databaseReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference databaseReferencePosts = FirebaseDatabase.getInstance()
                        .getReference("posts");
                String pid = databaseReferencePosts.push().getKey();

                myAuth = FirebaseAuth.getInstance();
                String userName = dataSnapshot.child(myAuth.getCurrentUser().getUid())
                        .child("userName").getValue().toString();

                String userPost = postText.getText().toString();
                Post post = new Post(userName, userPost);

                databaseReferencePosts.child(pid).setValue(post);

                Toast.makeText(HomePageActivity.this, "Post başarıyla paylaşıldı!"
                        , Toast.LENGTH_SHORT).show();

                postText.setText("");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomePageActivity.this, "Post paylaşılamadı!"
                        , Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.postButton){
            addPost();
        }

    }

}
