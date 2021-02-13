package ru.eds2809.dnevnik.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.eds2809.dnevnik.R;
import ru.eds2809.dnevnik.models.Appraisal;

public class SubjectTimeTableAdapter extends RecyclerView.Adapter<SubjectTimeTableAdapter.SubjectTimeTableVH> {

    private List<Appraisal> appraisalList = new ArrayList<>();
    private final OnScoreClickListener scoreClickListener;

    public SubjectTimeTableAdapter(OnScoreClickListener scoreClickListener) {
        this.scoreClickListener = scoreClickListener;
    }


    @NonNull
    @Override
    public SubjectTimeTableVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.approisal_list_item, parent, false);
        return new SubjectTimeTableAdapter.SubjectTimeTableVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectTimeTableVH holder, int position) {
        Appraisal appraisal = appraisalList.get(position);
        holder.scoreView.setText(String.valueOf(appraisal.getScore()));
        holder.date.setText(appraisal.getEvaluationDateString());
        holder.itemView.setOnClickListener(v -> {
            scoreClickListener.click(appraisal);
        });
    }

    @Override
    public int getItemCount() {
        return appraisalList.size();
    }

    public void addOrUpdate(Appraisal appraisal) {
        if (appraisal != null) {
            boolean isUpdate = false;

            for (int i = 0; i < appraisalList.size(); i++) {
                if (appraisalList.get(i).getId().equals(appraisal.getId())) {
                    notifyItemChanged(i);
                    isUpdate = true;
                }
            }

            if (!isUpdate) {
                appraisalList.add(appraisal);
                notifyItemInserted(appraisalList.size());
            }
        }
    }

    public void deleteItem(int position) {
        appraisalList.remove(position);
        notifyItemRemoved(position);
    }

    public class SubjectTimeTableVH extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView scoreView;

        public SubjectTimeTableVH(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.approisal_item_date);
            scoreView = itemView.findViewById(R.id.approisal_item_score);
        }
    }

    public void setAppraisalList(List<Appraisal> appraisalList) {
        this.appraisalList = appraisalList;
        notifyDataSetChanged();

    }

    public List<Appraisal> getAppraisalList() {
        return appraisalList;
    }

    public interface OnScoreClickListener {
        void click(Appraisal appraisal);
    }

}
