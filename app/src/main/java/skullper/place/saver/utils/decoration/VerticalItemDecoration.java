package skullper.place.saver.utils.decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import skullper.place.saver.App;
import skullper.place.saver.R;

/**
 * Created by skullper on 29.06.18.
 * Contact the developer - sckalper@gmail.com
 * company - A2Lab
 */

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {

    private final int padding;
    private final int botPadding;

    public VerticalItemDecoration() {
        padding = App.getContext().getResources().getDimensionPixelOffset(R.dimen.view_padding);
        botPadding = App.getContext().getResources().getDimensionPixelOffset(R.dimen.small_view_padding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) == 0) outRect.top = padding;
        outRect.bottom = botPadding;
        outRect.left = padding;
        outRect.right = padding;
    }
}
