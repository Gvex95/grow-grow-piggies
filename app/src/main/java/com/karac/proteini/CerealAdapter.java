package com.karac.proteini;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CerealAdapter extends RecyclerView.Adapter<CerealAdapter.CerealViewHolder> {

    private ArrayList<Cereal> mCerealList;
    private HashMap<String, Integer> retDataHashMap = new HashMap<>();
    private onItemClickedListener mOnClickListener;


    public CerealAdapter(ArrayList<Cereal> cereals){
        mCerealList = cereals;
    }

    public interface onItemClickedListener{
        void onItemClicked(int position);
        void onItemLongClicked(int position);
    }

    public void setOnItemClickListener(onItemClickedListener listener){
        mOnClickListener = listener;
    }



    public static class CerealViewHolder extends RecyclerView.ViewHolder {
        private TextView mCerealName;
        private TextView mCerealProteinPercent;
        private EditText mCerealMass;
        private ImageView mCerealImage;

        public CerealViewHolder(View itemView, final onItemClickedListener listener) {
            super(itemView);

            mCerealImage = (ImageView) itemView.findViewById(R.id.imageView);
            mCerealName = (TextView) itemView.findViewById(R.id.cerealName);
            mCerealProteinPercent = (TextView) itemView.findViewById(R.id.percent);

            mCerealMass = (EditText) itemView.findViewById(R.id.gram);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClicked(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemLongClicked(position);
                        }
                    }
                    return true;
                }
            });
        }
    }

    @NonNull
    @Override
    public CerealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);

        View cerealView = inflater.inflate(R.layout.single_row, parent, false);

        CerealViewHolder cerealViewHolder = new CerealViewHolder(cerealView,
                mOnClickListener);

        return cerealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CerealViewHolder holder, int position) {
        final Cereal cereal = mCerealList.get(position);
        holder.mCerealImage.setImageResource(cereal.getResourceId());
        holder.mCerealName.setText(cereal.getName());
        String percent = cereal.getProteinPercent() + "%";
        holder.mCerealProteinPercent.setText(percent);
        holder.mCerealMass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editTextValue = holder.mCerealMass.getText().toString();
                if (!editTextValue.equals("")){
                    retDataHashMap.put(cereal.getName(), Integer.parseInt(editTextValue));
                }else{
                    retDataHashMap.put(cereal.getName(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCerealList.size();
    }

    public HashMap<String, Integer> getRetData(){
        return retDataHashMap;
    }
}
