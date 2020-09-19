package me.memeszz.aurora.gui;

import me.memeszz.aurora.gui.components.Button;
import me.memeszz.aurora.module.Module;
import me.memeszz.aurora.module.ModuleManager;
import me.memeszz.aurora.module.modules.gui.ClickGuiModule;
import me.memeszz.aurora.util.colour.Colors;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.render.RenderUtil;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;


public class  Frame
{
    public ArrayList<Component> components;
    public Module.Category category;
    private boolean open;
    private final int width;
    private int y;
    private int x;
    private float hue;
    private final int barHeight;
    private boolean isDragging;
    public int dragX;
    public int dragY;
    private int height;
    public Frame(final Module.Category cat) {
        this.components = new ArrayList<>();
        this.category = cat;
        this.width = 95;
        this.x = 5;
        this.y = 5;
        this.barHeight = 15;
        this.dragX = 0;
        this.open = true;
        this.isDragging = false;
        int tY = this.barHeight;
        for (final Module mod : ModuleManager.getModulesInCategory(cat)) {
            final Button modButton = new Button(mod, this, tY);
            this.components.add(modButton);
            tY += 13;
        }
        this.refresh();
    }


    public ArrayList<Component> getComponents() {
        return this.components;
    }

    public void setX(final int newX) {
        this.x = newX;
    }

    public void setY(final int newY) {
        this.y = newY;
    }

    public void setDrag(final boolean drag) {
        this.isDragging = drag;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(final boolean open) {
        this.open = open;
    }


    public void renderFrame() {
        Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, new Color(ClickGuiModule.red.getValue(), ClickGuiModule.green.getValue(), ClickGuiModule.blue.getValue(),244).getRGB());
        RenderUtil.drawGradientSideways(this.x, this.y, this.x + this.width, this.y + this.barHeight - 3.0f, this.dragY + this.y + 4, Colors.getColor(55, 177, 218));
        RenderUtil.drawGradientSideways(this.x, this.y, this.x + this.width, this.y + this.barHeight - 3.0f, this.dragY + this.y + 4, Colors.getColor(204, 77, 198));
        if (this.hue > 255.0f) {
            this.hue = 0.0f;
        }
        float h = this.hue;
        float h2 = this.hue + 85.0f;
        float h3 = this.hue + 170.0f;
        if (h > 255.0f) {
            h = 0.0f;
        }
        if (h2 > 255.0f) {
            h2 -= 255.0f;
        }
        if (h3 > 255.0f) {
            h3 -= 255.0f;
        }
        final Color color33 = Color.getHSBColor(h / 255.0f, (float)ClickGuiModule.rainbowSat.getValue(), (float)ClickGuiModule.rainbowBri.getValue());
        final Color color34 = Color.getHSBColor(h2 / 255.0f, (float)ClickGuiModule.rainbowSat.getValue(), (float)ClickGuiModule.rainbowBri.getValue());
        final Color color35 = Color.getHSBColor(h3 / 255.0f, (float)ClickGuiModule.rainbowSat.getValue(), (float)ClickGuiModule.rainbowBri.getValue());
        final int color36 = color33.getRGB();
        final int color37 = color34.getRGB();
        final int color38 = color35.getRGB();
        this.hue += 0.1;
        RenderUtil.rectangleBordered(this.x, y, this.x + this.width, this.y + this.barHeight, 0.5, Colors.getColor(90), Colors.getColor(0));
        RenderUtil.rectangleBordered(this.x, y, this.x + this.width, this.y + this.barHeight, 1.0, Colors.getColor(90), Colors.getColor(61));
        RenderUtil.drawGradientSideways(this.x, this.y - 3, this.x + this.width, this.y , color36, color37);
        RenderUtil.drawGradientSideways(this.x, this.y - 3, this.x + this.width, this.y, color37, color38);
        drawCentredString(this.category.name(), x + this.width / 2, -1);
        if (this.open && !this.components.isEmpty()) {
            for (final Component component : this.components) {
                component.renderComponent();
            }
        }

    }
    
    public void refresh() {
        int off = this.barHeight;
        for (final Component comp : this.components) {
            comp.setOff(off);
            off += comp.getHeight();
        }
        this.height = off;
    }

    private void drawCentredString(String text, int x, int color) {
        FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), text, x - FontUtils.getStringWidth(ClickGuiModule.customFont.getValue(), text) / 2, this.y +3, color);
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void updatePosition(final int mouseX, final int mouseY) {
        if (this.isDragging) {
            this.setX(mouseX - this.dragX);
            this.setY(mouseY - this.dragY);
        }
    }
    
    public boolean isWithinHeader(final int x, final int y) {
        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
    }
}
