package com.example.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopping.R;
import com.example.shopping.bean.ParticularsBean;

import java.util.List;

public class ProblemAdapter extends RecyclerView.Adapter<ProblemAdapter.ViewHolder> {
    List<ParticularsBean.DataBeanX.IssueBean> issueBeans;
    Context context;

    public ProblemAdapter(List<ParticularsBean.DataBeanX.IssueBean> issueBeans, Context context) {
        this.issueBeans = issueBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.problem_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ParticularsBean.DataBeanX.IssueBean issueBean = issueBeans.get(position);
        holder.problem_tv_title.setText("· "+issueBean.getQuestion());
        holder.problem_tv_content.setText(issueBean.getAnswer());

    }

    @Override
    public int getItemCount() {
        return issueBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView problem_tv_title;
        TextView problem_tv_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problem_tv_title = itemView.findViewById(R.id.problem_tv_title);
            problem_tv_content = itemView.findViewById(R.id.problem_tv_content);
        }
    }
}
