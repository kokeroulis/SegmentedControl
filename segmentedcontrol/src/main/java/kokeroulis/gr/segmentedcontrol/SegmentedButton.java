/*
 * Copyright (C) 2015 Antonis Tsiapaliokas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kokeroulis.gr.segmentedcontrol;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

public class SegmentedButton extends RadioButton {
    public SegmentedButton(Context context) {
        this(context, null, false, false);
    }

    public SegmentedButton(Context context, AttributeSet attrs) {
        this(context, attrs, false, false);
    }

    public SegmentedButton(Context context, AttributeSet attrs, boolean hasLeftRadius, boolean hasRightRadius) {
        super(context, attrs, R.attr.buttonStyle);
        draw(hasLeftRadius, hasRightRadius);
    }

    private void draw(boolean hasLeftRadius, boolean hasRightRadius) {
        final int colorPrimary = getResources().getColor(R.color.colorPrimary);
        final int colorAccent = getResources().getColor(R.color.colorAccent);

        SegmentedShape shape = new SegmentedShape(colorPrimary, colorAccent, 20);
        setBackground(shape.buildSelectorShapeFromColors(hasLeftRadius, hasRightRadius));
        setTextColor(shape.getColorStateList());
    }
}