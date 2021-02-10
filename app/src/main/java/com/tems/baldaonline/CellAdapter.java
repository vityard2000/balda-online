package com.tems.baldaonline;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CellAdapter extends RecyclerView.Adapter<CellAdapter.CellViewHolder> {

    private static final String myTag = "debugTag";
    private final List<Cell> cells;

    public CellAdapter(List<Cell> cells) {
        this.cells = cells;
        int cont = 3;
        StringBuilder tmp;
        for (int i = 0; i < cont; i++) {
            tmp = new StringBuilder();
            for (int j = 0; j < cont; j++) {
                tmp.append("[").append(cells.get(i * cont + j).letter).append("]");
            }
            Log.d(myTag, tmp.toString());
        }

    }

    @NonNull
    @Override
    public CellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate создает новый объект java из xml файла
        // false говорит о том, что нам не нужно помещать наш объект внутрь родительского т.к. recyclerView и так это сделает
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_list_item, parent, false);
        Log.d(myTag, "создали холдер");

        return new CellViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CellViewHolder holder, int position) {
        holder.bind(cells.get(position));
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    static class CellViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewCellList; // ячейка

        @SuppressLint("ClickableViewAccessibility")
        public CellViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCellList = itemView.findViewById(R.id.cell_list_item__bt__letter);




            textViewCellList.setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            Log.d(myTag, "ACTION_MOVE");
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            Log.d(myTag, "ACTION_CANCEL");
                            break;
                        case MotionEvent.ACTION_DOWN:
                            Log.d(myTag, "ACTION_DOWN");
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d(myTag, "ACTION_UP");
                            break;
                    }
                    return true;
                }
            });
        }

        // полученные данные в textView
        private void bind(@NonNull Cell cell) {
            Log.d(myTag, "создали начали выводить текст в textView");
            textViewCellList.setText(Character.toString(cell.letter));
            Log.d(myTag, "более того смогли его вывести");
        }
    }
}
