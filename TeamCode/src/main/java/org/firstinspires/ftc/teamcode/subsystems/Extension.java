package org.firstinspires.ftc.teamcode.subsystems;

import org.screamrobotics.SuperSCREAMLib.hardware.Slides;

public class Extension {
    public Slides slides;
    public enum slidePosition{
        HOME(0),
        LOWBUCKET(-1192),
        HIGHBUCKET(-2884);
        public final int value;
        slidePosition(int position) {
            value = position;
        }
    }

    public Extension(Slides m_slides){
        slides = m_slides;
    }

    public void goToPosition(slidePosition position) {
        slides.goToPosition(position.value);
    }
}
