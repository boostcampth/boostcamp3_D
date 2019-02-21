package com.teamdonut.eatto.ui.mypage.judge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.teamdonut.eatto.R;
import com.teamdonut.eatto.common.BaseRecyclerViewAdapter;
import com.teamdonut.eatto.data.Board;
import com.teamdonut.eatto.databinding.MypageJudgeItemBinding;

import java.util.List;

public class JudgeAdapter extends BaseRecyclerViewAdapter<Board, JudgeAdapter.ViewHolder> {
    private MyPageJudgeViewModel mViewModel;
    private final int SCORE_GREAT = 5;
    private final int SCORE_GOOD = 3;
    private final int SCORE_NORMAL = 2;

    public JudgeAdapter(List<Board> dataSet, MyPageJudgeViewModel myPageJudgeViewModel) {
        super(dataSet);
        mViewModel = myPageJudgeViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_judge_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindView(ViewHolder holder, int position) {
        Board board = getItem(position);
        holder.binding.tvGreat.setOnClickListener(v -> {
            mViewModel.judgeBoard(board.getWriterId(), board.getId(), SCORE_GREAT);
            removeItem(position);
        });

        holder.binding.tvGood.setOnClickListener(v -> {
            mViewModel.judgeBoard(board.getWriterId(), board.getId(), SCORE_GOOD);
            removeItem(position);
        });

        holder.binding.tvNormal.setOnClickListener(v -> {
        mViewModel.judgeBoard(board.getWriterId(), board.getId(), SCORE_NORMAL);
            removeItem(position);
        });
        holder.binding.setBoard(getItem(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MypageJudgeItemBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}