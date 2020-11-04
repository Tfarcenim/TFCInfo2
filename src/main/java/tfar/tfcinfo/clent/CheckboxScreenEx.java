package tfar.tfcinfo.clent;

import net.minecraftforge.fml.client.config.GuiCheckBox;

public class CheckboxScreenEx extends CheckboxScreen {

    protected void drawForeground() {

        super.drawForeground();

        int xPos = guiLeft + 24;

        xPos += 135;
        int yPos = guiTop + 2;

        this.fontRenderer.drawString("Min Temp", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Biome", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Moon Phase", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Light Level", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Flora", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Trees", xPos, yPos += spacing, 0x404040);
        this.fontRenderer.drawString("Local Difficulty", xPos, yPos += spacing, 0x404040);

    }

    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        int xPos = guiLeft + 10;
        int yPos;
        xPos += 135;
        yPos = guiTop;
        for (int i = TFCInfoClientConfig.class.getFields().length - 7; i < TFCInfoClientConfig.class.getFields().length;i++) {
            try {
                addButton(new GuiCheckBox(i, xPos, yPos += spacing, "", TFCInfoClientConfig.class.getFields()[i].getBoolean(null)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
