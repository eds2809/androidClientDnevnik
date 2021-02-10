package ru.eds2809.dnevnik.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.eds2809.dnevnik.R;
import ru.eds2809.dnevnik.models.Subject;

public class SubjectListRVAdapter extends RecyclerView.Adapter<SubjectListRVAdapter.SubjectViewHolder> {

    private List<Subject> subjectList;
    private OnClickListener onClickListener;

    public SubjectListRVAdapter(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subjet_list_item, parent, false);
        return new SubjectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.subjectNameTextView.setText(subject.getName());
        holder.itemView.setOnClickListener(v -> {
            onClickListener.click(subject);
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public class SubjectViewHolder extends RecyclerView.ViewHolder {

        private TextView subjectNameTextView;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            subjectNameTextView = (TextView) itemView.findViewById(R.id.subject_name_view_item);
        }
    }

    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
        notifyDataSetChanged();
    }

    public interface OnClickListener {
        void click(Subject subject);
    }
}
