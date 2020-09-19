package me.memeszz.aurora.gui.components;

import me.memeszz.aurora.gui.Component;
import me.memeszz.aurora.module.modules.gui.ClickGuiModule;
import me.memeszz.aurora.util.font.FontUtils;
import me.memeszz.aurora.util.setting.Setting;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class CheckBox extends Component
{
    private boolean hovered;
    private final Setting.b op;
    private final Button parent;
    private int offset;
    private int x;
    private int y;
    
    public CheckBox(final Setting.b option, final Button button, final int offset) {
        this.op = option;
        this.parent = button;
        this.x = button.parent.getX() + button.parent.getWidth();
        this.y = button.parent.getY() + button.offset;
        this.offset = offset;
    }
    
    @Override
    public void renderComponent() {
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset + 1, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 16, this.hovered ? (this.op.getValue() ?
                new Color(ClickGuiModule.red.getValue(), ClickGuiModule.green.getValue(),ClickGuiModule.blue.getValue(), ClickGuiModule.alpha.getValue()).getRGB() :
                new Color(0, 0, 0, 150).darker().darker().getRGB()) : (this.op.getValue() ?
                new Color(ClickGuiModule.red.getValue(), ClickGuiModule.green.getValue(),ClickGuiModule.blue.getValue(), ClickGuiModule.alpha.getValue()).getRGB() :
                new Color(0, 0, 0, 150).getRGB()));
        Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 1, new Color(0, 0, 0, 150).getRGB());
        FontUtils.drawStringWithShadow(ClickGuiModule.customFont.getValue(), this.op.getName(), this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 4, -1);
    }
    
    @Override
    public void setOff(final int newOff) {
        this.offset = newOff;
    }
    
    @Override
    public void updateComponent(final int mouseX, final int mouseY) {
        this.hovered = this.isMouseOnButton(mouseX, mouseY);
        this.y = this.parent.parent.getY() + this.offset;
        this.x = this.parent.parent.getX();
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
            this.op.setValue(!this.op.getValue());
        }
    }
    
    public boolean isMouseOnButton(final int x, final int y) {
        return x > this.x && x < this.x + 95 && y > this.y && y < this.y + 16;
    }
}
