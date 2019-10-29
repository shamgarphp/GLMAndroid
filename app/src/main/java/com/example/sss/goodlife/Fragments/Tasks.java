package com.example.sss.goodlife.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sss.goodlife.Adapters.DayListAdapter;
import com.example.sss.goodlife.Adapters.LocationAdapter;
import com.example.sss.goodlife.Adapters.ParticipantsListAdapter;
import com.example.sss.goodlife.Adapters.ParticipantsTypeAdapter;
import com.example.sss.goodlife.Api.APIUrl;
import com.example.sss.goodlife.Api.ApiService;
import com.example.sss.goodlife.MainActivity;
import com.example.sss.goodlife.Models.DayListModel;
import com.example.sss.goodlife.Models.Locations;
import com.example.sss.goodlife.Models.PartcipantListModel;
import com.example.sss.goodlife.Models.ParticipantsTypeIds;
import com.example.sss.goodlife.Models.ParticipantsTypeStatus;
import com.example.sss.goodlife.Models.Status;
import com.example.sss.goodlife.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Tasks extends Fragment{

    DayListAdapter ladp;
    ParticipantsListAdapter padp;
    ListView dayLV;
    Button add,addBtn1;
    private LinearLayout VbsParentLinearLayout2;

    ArrayList<DayListModel> listModel = new ArrayList<>();
    ArrayList<PartcipantListModel> partcipantListModels = new ArrayList<>();
    ArrayList<DayListModel> listModel2 = new ArrayList<>();
    Activity activity;
    //Widgents
    private ImageButton VbsAddEventDateColumn,delete_button,VbsAddParticipaintsColumn,delete_button_participaints;
    private TextView selectDateTxt,selectStartTime,selectEndTime,participaintDate;
    private Spinner spinnerStartTimeEvent,spinnerEndTimeEvent,spinnerParticipaintsList,spinnerParticipaintsList1,location;
    private EditText participaints_num_men,participaints_num_women,participaints_num_child,participaintsName,participaintsPhone,participaintsDescription,VbsProgramAim;
    private Button VbsApplicationSubmit;

    private ArrayList<TextView> selectedEventDates=new ArrayList<>();
    private ArrayList<TextView> selectedEventStartTime=new ArrayList<>();
    private ArrayList<TextView> selectedEventEndTime=new ArrayList<>();

    private ArrayList<EditText> selectedNumOfMen=new ArrayList<>();
    private ArrayList<EditText> selectedNumOfWomen=new ArrayList<>();
    private ArrayList<EditText> selectedNumOfChild=new ArrayList<>();
    private ArrayList<EditText> selectedPar_names=new ArrayList<>();
    private ArrayList<EditText> selectedPar_phone=new ArrayList<>();
    private ArrayList<EditText> selectedPar_des=new ArrayList<>();
    private ArrayList<TextView> participantDates=new ArrayList<>();

    private ArrayList<String> ListselectedEventDates=new ArrayList<>();
    private ArrayList<String> ListselectedEventStartTime=new ArrayList<>();
    private ArrayList<String> ListselectedEventEndTime=new ArrayList<>();

    private ArrayList<String> ListselectedNumOfMen=new ArrayList<>();
    private ArrayList<String> ListselectedNumOfWomen=new ArrayList<>();
    private ArrayList<String> ListselectedNumOfChild=new ArrayList<>();
    private ArrayList<String> ListselectedPar_names=new ArrayList<>();
    private ArrayList<String> ListselectedPar_ids=new ArrayList<>();
    private ArrayList<String> ListselectedPar_phone=new ArrayList<>();
    private ArrayList<String> ListselectedPar_des=new ArrayList<>();
    private ArrayList<String> ListparticipantDates=new ArrayList<>();


    private ArrayList<String> programNamesDropDownArrayList=new ArrayList<>();



    ArrayList<String> state = new ArrayList<String>();
    private ArrayAdapter adapter;
    private LocationAdapter locationAdapter;
    private ParticipantsTypeAdapter participantsTypeAdapter;

    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private String selectedItemStartEvent,selectedItemEndEvent;

    //Api calls
    private ApiService apiService;
    private List<Locations> locationTypes;
    private List<ParticipantsTypeIds> participantsTypeIds;
    private String locationId,participantId;
    private ProgressDialog progressDialog,progressDialog1,gettingParticipantsList;



    public Tasks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Tasks Application");
        final View view= inflater.inflate(R.layout.fragment_vbs_task_application, container, false);

        activity = getActivity();
        dayLV = view.findViewById(R.id.dayList);
        add = view.findViewById(R.id.addBtn);
        addBtn1 = view.findViewById(R.id.addBtn1);
        VbsParentLinearLayout2=(LinearLayout)view.findViewById(R.id.VbsParentLinearLayout2);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRows();
            }
        });
        addRows();
        addParticipantRows();

//        addBtn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                addParticipantRows();
//            }
//        });


        location = (Spinner) view.findViewById(R.id.VbsLocation);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setTitle("Applying Program application");
        progressDialog.setMessage("Please wait, while we are submitting your details ");
        progressDialog.setCanceledOnTouchOutside(false);

        gettingParticipantsList=new ProgressDialog(getActivity());
        gettingParticipantsList.setTitle("Getting Data");
        gettingParticipantsList.setMessage("Please wait..., ");
        gettingParticipantsList.setCanceledOnTouchOutside(false);

        progressDialog1=new ProgressDialog(getActivity());
        progressDialog1.setTitle("Getting Data");
        progressDialog1.setMessage("Please wait...,");
        progressDialog1.setCanceledOnTouchOutside(false);
        progressDialog1.show();

        //initializing widgets
        VbsParentLinearLayout2=(LinearLayout)view.findViewById(R.id.VbsParentLinearLayout2);
        VbsAddEventDateColumn=view.findViewById(R.id.VbsAddEventDateColumn);
        VbsAddParticipaintsColumn=view.findViewById(R.id.VbsAddParticipaintsColumn);
        VbsProgramAim=view.findViewById(R.id.VbsProgramAim);
        VbsApplicationSubmit=view.findViewById(R.id.VbsApplicationSubmit);


        //Spinner Dropdown for location
        apiService= APIUrl.getApiClient().create(ApiService.class);
        //addEventDate dynamic

        addBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addParticipantRows();
            }
        });

        progressDialog1.dismiss();
        return view;
    }

    public void addRows(){
        DayListModel model =  new DayListModel();
        model.setDay("Day");
        model.setStartTime("Start Time");
        model.setEndTime("End Time");
        listModel.add(model);
        ladp = new DayListAdapter(activity,listModel);

        ladp.notifyDataSetChanged();

        dayLV.setAdapter(ladp);

        setListViewHeightBasedOnChildren(dayLV);
    }

 public void addParticipantRows(){
     LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     final View rowView = inflater.inflate(R.layout.task_participants_list_dynamic_field, null);
     // Add the new row before the add field button.

//     if (VbsParentLinearLayout2.getChildCount()==0){
//         VbsParentLinearLayout2.addView(rowView);
//     }else {
        /* if (participaintDate.getText().toString().contains("Select Date")){
             Toast.makeText(getActivity(),"please fill details first",Toast.LENGTH_LONG).show();
             return;
         }
         if (TextUtils.isEmpty(participaints_num_men.getText().toString())){
             Toast.makeText(getActivity(),"please fill details first",Toast.LENGTH_LONG).show();
             participaints_num_men.setError("field cannot be blank");
             return;
         }
         if (TextUtils.isEmpty(participaints_num_women.getText().toString())){
             Toast.makeText(getActivity(),"please fill details first",Toast.LENGTH_LONG).show();
             participaints_num_women.setError("field cannot be blank");
             return;
         }
         if (TextUtils.isEmpty(participaints_num_child.getText().toString())){
             Toast.makeText(getActivity(),"please fill details first",Toast.LENGTH_LONG).show();
             participaints_num_child.setError("field cannot be blank");
             return;
         }*/
         VbsParentLinearLayout2.addView(rowView);
//     }

     participaints_num_men=rowView.findViewById(R.id.num_men);
     participaints_num_women=rowView.findViewById(R.id.num_women);
     participaints_num_child=rowView.findViewById(R.id.num_child);
     participaintDate=rowView.findViewById(R.id.participaintDate);
     spinnerParticipaintsList=rowView.findViewById(R.id.participaintsSpinner);
     spinnerParticipaintsList1=rowView.findViewById(R.id.participaintsSpinner1);
     delete_button_participaints=rowView.findViewById(R.id.delete_button_participaints);
     participaintsName=rowView.findViewById(R.id.participaintsName);
     participaintsPhone=rowView.findViewById(R.id.participaintsPhone);
     participaintsDescription=rowView.findViewById(R.id.participaintsDescription);

//     gettingParticipantsList.show();

     spinnerParticipaintsList.setPrompt("Select");
     spinnerParticipaintsList1.setPrompt("Select");
//     participantsTypeAdapter = new ParticipantsTypeAdapter(getActivity(), (ArrayList<ParticipantsTypeIds>) participantsTypeIds);
//     spinnerParticipaintsList.setAdapter(participantsTypeAdapter);

     // Spinner Drop down elements
     List<String> categories = new ArrayList<String>();
     categories.add("Select");
     categories.add("Vehicles");
     categories.add("Books");
     categories.add("People");

     // Creating adapter for spinner
     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

     // Drop down layout style - list view with radio button
     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

     // attaching data adapter to spinner
     spinnerParticipaintsList.setAdapter(dataAdapter);


     List<String> categories1 = new ArrayList<String>();
     categories1.add("Select");
     categories1.add("Ravi");
     categories1.add("Harish");
     categories1.add("Ramu");

     // Creating adapter for spinner
     ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories1);

     // Drop down layout style - list view with radio button
     dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     spinnerParticipaintsList1.setAdapter(dataAdapter1);


     //Spinner Dropdown for location
    /* apiService= APIUrl.getApiClient().create(ApiService.class);
     final retrofit2.Call<ParticipantsTypeStatus> call=apiService.getParticipantsIds();
     call.enqueue(new Callback<ParticipantsTypeStatus>() {
         @Override
         public void onResponse(retrofit2.Call<ParticipantsTypeStatus> call, Response<ParticipantsTypeStatus> response) {
             if (response.body().getMessage().size()!=0) {
                 gettingParticipantsList.dismiss();
                 participantsTypeIds = response.body().getMessage();
                 spinnerParticipaintsList.setPrompt("Select Location");
                 participantsTypeAdapter = new ParticipantsTypeAdapter(getActivity(), (ArrayList<ParticipantsTypeIds>) participantsTypeIds);
                 spinnerParticipaintsList.setAdapter(participantsTypeAdapter);
             }
             else {
                 gettingParticipantsList.dismiss();
                 Toast.makeText(getActivity(),"Programs not forund",Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onFailure(retrofit2.Call<ParticipantsTypeStatus> call, Throwable t) {
             Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
             gettingParticipantsList.dismiss();
         }
     });
*/



     participaintDate.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             calendar = Calendar.getInstance();
             year = calendar.get(Calendar.YEAR);
             month = calendar.get(Calendar.MONTH);
             dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

             DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                     new DatePickerDialog.OnDateSetListener() {
                         @Override
                         public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                             participaintDate.setText(day + "/" + (month + 1)+ "/" + year);
                         }
                     }, year, month, dayOfMonth);

             datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
             datePickerDialog.show();
         }
     });

     selectedNumOfMen.add(participaints_num_men);
     selectedNumOfWomen.add(participaints_num_women);
     selectedNumOfChild.add(participaints_num_child);
     selectedPar_names.add(participaintsName);
     selectedPar_phone.add(participaintsPhone);
     selectedPar_des.add(participaintsDescription);
     participantDates.add(participaintDate);

     delete_button_participaints.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             VbsParentLinearLayout2.removeView((View) v.getParent());

             selectedNumOfMen.remove(participaints_num_men);
             selectedNumOfWomen.remove(participaints_num_women);
             selectedNumOfChild.remove(participaints_num_child);
             selectedPar_names.remove(participaintsName);
             selectedPar_phone.remove(participaintsPhone);
             selectedPar_des.remove(participaintsDescription);
             participantDates.remove(participaintDate);
         }
     });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
