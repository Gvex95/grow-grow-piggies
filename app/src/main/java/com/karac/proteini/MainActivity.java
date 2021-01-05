package com.karac.proteini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ChangePercentDialog.ChangePercentDialogListener, AddNewCerealDialog.AddNewCerealListener {

    private ArrayList<Cereal> mCerealList = new ArrayList<>();
    private RecyclerView mRecycleView;
    private CerealAdapter mCerealAdapter;
    private FloatingActionButton add_new;
    private FloatingActionButton calculate;
    private HashMap<String, Integer> mData;
    private int mChangePercentPosition;
    private Result mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get default cereal list
        getDefaultCerealList();

        buildRecycleView();

        add_new = (FloatingActionButton) findViewById(R.id.fab_add_new);
        calculate = (FloatingActionButton) findViewById(R.id.fab_calculate);

        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_new_item();
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate_percent();
                if (mResult.getTotalMass() != 0){
                    Bundle bundle = new Bundle();
                    bundle.putFloat("totalMass", mResult.getTotalMass());
                    bundle.putFloat("totalProteinMass", mResult.getTotalProteinMass());
                    bundle.putFloat("totalProteinPercent", mResult.getProteinPercent());
                    ShowResultDialog showResultDialog = new ShowResultDialog();
                    showResultDialog.setArguments(bundle);
                    showResultDialog.show(getSupportFragmentManager(), "show result dialog!");
                }
            }
        });


    }

    private void changeProteinPercent(int position){
        mChangePercentPosition = position;
        String cerealName = mCerealList.get(position).getName();

        ChangePercentDialog changePercentDialog = new ChangePercentDialog();
        Bundle bundle = new Bundle();
        bundle.putString("cerealName", cerealName);
        changePercentDialog.setArguments(bundle);
        changePercentDialog.show(getSupportFragmentManager(), "change percent dialog!");
    }

    private void deleteCereal(int position){
        mCerealList.remove(position);
        mCerealAdapter.notifyItemRemoved(position);
    }

    private void getDefaultCerealList(){
        Cereal kukuruz = new Cereal(R.drawable.ic_corn, "kukuruz", 25);
        Cereal soja = new Cereal(R.drawable.ic_soy, "soja", 25);
        Cereal jecam = new Cereal(R.drawable.ic_barley, "jecam", 14);
        Cereal suncokret = new Cereal(R.drawable.ic_sunflower, "suncokret", 68);

        mCerealList.add(kukuruz);
        mCerealList.add(soja);
        mCerealList.add(jecam);
        mCerealList.add(suncokret);
    }

    private void add_new_item(){
        AddNewCerealDialog addNewCerealDialog = new AddNewCerealDialog();
        addNewCerealDialog.show(getSupportFragmentManager(), "add new cereal dialog!");
    }

    private void calculate_percent(){
        mData = mCerealAdapter.getRetData();
        float totalProteinMass = 0;
        float totalMass = 0;

        for (Cereal c : mCerealList){
            if (mData.containsKey(c.getName())){
                Log.d("GVEX", c.getName() + " " + mData.get(c.getName()));
                int massInGram = mData.get(c.getName());
                float perCereal = (massInGram * c.getProteinPercent()) / 100;
                Log.d("GVEX", "Grams of proteins for cereal: " + c.getName() + " is " + perCereal + " and percent" +
                                " of protein for that cereal is: " + c.getProteinPercent());
                totalProteinMass += perCereal;
                totalMass += massInGram;
            }
        }
        float res = 100 * totalProteinMass / totalMass;
        mResult = new Result(totalMass, totalProteinMass, res);
    }

    public void buildRecycleView(){
        mCerealAdapter = new CerealAdapter(mCerealList);
        mCerealAdapter.setOnItemClickListener(new CerealAdapter.onItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                changeProteinPercent(position);
            }

            @Override
            public void onItemLongClicked(int position) {
                deleteCereal(position);
            }
        });

        mRecycleView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecycleView.setAdapter(mCerealAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    add_new.show();
                    calculate.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && add_new.isShown()) {
                    add_new.hide();
                }
                if (dy > 0 || dy < 0 && calculate.isShown()){
                    calculate.hide();
                }
            }
        });
    }

    @Override
    public void changePercent(float newPercent) {
        mCerealList.get(mChangePercentPosition).setProteinPercent(newPercent);
        mCerealAdapter.notifyItemChanged(mChangePercentPosition);
    }

    @Override
    public void addCereal(String name, String percent) {
        mCerealList.add(new Cereal(R.drawable.ic_no_image, name, Float.parseFloat(percent)));
        mCerealAdapter.notifyItemInserted(mCerealList.size());
    }
}
