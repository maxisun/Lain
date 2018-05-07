package com.example.max00.lain.Fragments;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.max00.lain.Adapters.RecyclerViewAdapter;
import com.example.max00.lain.Class.Contacto;
import com.example.max00.lain.R;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;
import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private View v;
    private Contacto editcontact;
    private String position;
    private SearchView searchView;
    List<Contacto> list = new ArrayList<>();
    List<Contacto> backup = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1,String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 3){
            if(data.hasExtra("New_Contact")==true){
                Contacto getcontacto =data.getParcelableExtra("New_Contact");
                Contacto new_contact = new Contacto(getcontacto.getNombre(),getcontacto.getApellido(),getcontacto.getEmail(),getcontacto.getPhone(),getcontacto.getDate(),getcontacto.getUri(),getcontacto.getCheck());
                list.add(new_contact);
                backup.add(new_contact);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_contacts, container, false);
        recyclerView = v.findViewById(R.id.recyclerView_contacts);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        RecyclerView.LayoutManager layoutManager = gridLayoutManager;
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(getContext(), (ArrayList<Contacto>) getContactos());
        recyclerView.setAdapter(adapter);


        searchView = getActivity().findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                list.clear();
                query.toLowerCase();
                for(int i=0; i < backup.size(); i++){
                    if(backup.get(i).getNombre().toLowerCase().contains(query) || backup.get(i).getPhone().contains(query) ){
                        list.add(backup.get(i));
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();
                if(newText.length()==0) {
                    list.addAll(backup);
                    adapter.notifyDataSetChanged();
                }else {
                    newText.toLowerCase();
                    for(int i=0; i < backup.size(); i++){
                        if(backup.get(i).getNombre().toLowerCase().contains(newText) || backup.get(i).getPhone().contains(newText)){
                            list.add(backup.get(i));
                        }
                    }
                    adapter.notifyDataSetChanged();
                    return true;
                }
                return true;
            }
        });
        return v;
    }

    //@RequiresApi(api = Build.VERSION_CODES.ECLAIR_0_1)
    /*private List<Contacto> getContactos(){
        //Cursor cursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null, ContactsContract.Contacts.DISPLAY_NAME+"ASC");
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            list.add(new Contacto(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),R.drawable.holi));
        }
        cursor.close();
        return list;
    }*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<Contacto> getContactos(){
        String name = "";
        String apellido = "";
        String email = "";
        String telefono = "";
        String fecha = "";
        String imagen = "";
        Boolean bool = false;
        Uri uri = uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.perfil) + '/' + getResources().getResourceTypeName(R.drawable.perfil) + '/' + getResources().getResourceEntryName(R.drawable.perfil) );

        //uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getResources().getResourcePackageName(R.drawable.judge) + '/' + getResources().getResourceTypeName(R.drawable.judge) + '/' + getResources().getResourceEntryName(R.drawable.judge) );
        //Cursor cursor= getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,null,null,ContactsContract.Contacts.DISPLAY_NAME+" ASC");
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,ContactsContract.Contacts.DISPLAY_NAME+" ASC");
        cursor.moveToFirst();
        while(cursor.moveToNext()){
            //list.add(new Contacto(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),"",cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID)),"","",R.drawable.holi));
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            telefono = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            imagen = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
            email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID));

            if(name==null && name.equals("")){
                name = "Unavailable";
            }

            if(telefono==null && telefono.equals("")){
                telefono = "Unavailable";
            }

            if(email == null && email.equals("")){
                email = "Unavailable";
            }

            if(imagen!=null){
                list.add(new Contacto(name,apellido,email,telefono,fecha,Uri.parse(imagen),bool));
                backup.add(new Contacto(name,apellido,email,telefono,fecha,Uri.parse(imagen),bool));
            }else {
                list.add(new Contacto(name,apellido,email,telefono,fecha,uri,bool));
                backup.add(new Contacto(name,apellido,email,telefono,fecha,uri,bool));
            }
        }
        cursor.close();
        return list;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        editcontact = null;
        position = null;

        Intent confirmation = getActivity().getIntent();
        if(confirmation.getStringExtra(Intent.EXTRA_TEXT) != null){
            Intent intent = getActivity().getIntent();
            Bundle bundle = intent.getExtras();
            editcontact = bundle.getParcelable("EditContact");
            position = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (backup.get(parseInt(position)) != editcontact) {
                list.set(parseInt(position), editcontact);
                backup.set(parseInt(position), editcontact);
            }
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
