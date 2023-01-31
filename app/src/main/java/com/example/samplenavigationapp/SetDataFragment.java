package com.example.samplenavigationapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.bson.Document;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;


public class SetDataFragment extends Fragment {

    public SetDataFragment() {
        // Required empty public constructor
    }

    EditText StudentName;
    EditText StudentAddress;
    EditText StudentEmail;
    Button Submit;

    App app;
    String appID ="application-0-tnijw";

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view= inflater.inflate(R.layout.fragment_set_data, container, false);

        StudentName=view.findViewById(R.id.StudentName);
        StudentAddress=view.findViewById(R.id.StudentAddress);
        StudentEmail=view.findViewById(R.id.StudentEmail);
        Submit =view.findViewById(R.id.Submit);
        Realm.init(getContext());
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create a entry in database

                // ObjectId _id = new ObjectId(StudentId.getText().toString());
                //task1.set_id(_id);
//                task1.setName(StudentName.getText().toString());
//                task1.setAddress(StudentAddress.getText().toString());
//                task1.setEmail(StudentEmail.getText().toString());


                // context, usually an Activity or Application/
                app = new App(new AppConfiguration.Builder(appID)
                        .build());
                Credentials credentials = Credentials.anonymous();
                app.loginAsync(credentials, result -> {
                    if (result.isSuccess()) {
                        Log.v("QUICKSTART", "Successfully authenticated anonymously.");
                        User user = app.currentUser();
                        mongoClient = user.getMongoClient("mongobd-atlas");
                        mongoDatabase=mongoClient.getDatabase("Student");
                        MongoCollection<Document> mongoCollection= mongoDatabase.getCollection("Student1");
                        mongoCollection.insertOne(new Document("userId",user.getId()).append("name",StudentName.getText().toString())
                                        .append("email",StudentEmail.getText().toString()).append("address",StudentAddress.getText().toString()))
                                .getAsync(result1 -> {
                                    if (result1.isSuccess()){
                                        Log.v("DATABASE", "Inserted Successfully");
                                    }
                                    else{
                                        Log.v("DATABASE--", result1.getError().toString());
                                    }
                                });

                    } else {
                        Log.e("QUICKSTART", "Failed to log in. Error: " + result.getError());
                    }
                });
                // TODO :- exit fragment after inserting adata


            }
        });




        return view;
    }
}