package timothyyudi.tryendless;

import android.view.View;

/**
 * Created by root on 10/8/2015.
 */
public interface RVItemClickListener {
    void onItemClick(View v, int position);
    void onItemButtonClick(View v, int position);
    void onLongItemClick(View v, int position);
}
