package com.skyresourcesclassic.base.guide;

import com.skyresourcesclassic.base.guide.gui.GuideGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GuideImageButton extends GuidePageButton {
    private GuideImage image;

    public GuideImageButton(String imgLink, String display, ItemStack stack) {
        super(display, stack);
        image = new GuideImage(imgLink);
    }

    @Override
    public void setArguments(String[] allArgs) {
        image = new GuideImage(allArgs[3]);
    }

    @Override
    public boolean onClicked() {

        GuiScreen screen = Minecraft.getInstance().currentScreen;
        if (screen instanceof GuideGUI) {
            ((GuideGUI) screen).openImage(image);
        }

        return false;
    }

    @Override
    public String getIdentifier() {
        return "image";
    }

    @Override
    public GuidePageButton clone() {
        return new GuideImageButton(image.imgAddress, this.display, this.stackDisplay);
    }

    @Override
    public List<String> getHoverDisplay()
    {
        List<String> list = new ArrayList<>();
        list.add("Click to go to image");
        return list;
    }
}
