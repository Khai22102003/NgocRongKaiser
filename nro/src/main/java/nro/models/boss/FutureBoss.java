package nro.models.boss;

import nro.models.player.Player;
import nro.models.skill.Skill;
import nro.server.io.Message;

public abstract class FutureBoss extends Boss {

    public FutureBoss(int id, BossData data) {
        super(id, data);
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (plAtt != null) {
            // Kiểm tra kỹ năng của người chơi và giảm sát thương 20% cho các kỹ năng cụ thể
            switch (plAtt.playerSkill.skillSelect.template.id) {
                case Skill.TU_SAT:
                case Skill.QUA_CAU_KENH_KHI:
                case Skill.MAKANKOSAPPO:
                    damage = (damage * 80) / 100; // Giảm sát thương 20% cho các kỹ năng cụ thể
                    break;
                // Không áp dụng giảm cho các kỹ năng khác
                default:
                    break;
            }
            // Gọi phương thức xử lý sát thương của Boss với sát thương đã điều chỉnh
            return super.injured(plAtt, damage, piercing, isMobAttack);
        } else {
            return 0; // Nếu không có người tấn công, trả về 0 sát thương
        }
    }

    // Phương thức để ẩn NPC khỏi người chơi
    public void hide_npc(Player player, int idnpc, int action) {
        Message msg;
        try {
            msg = new Message(-73);  // Tạo thông điệp mới với mã -73
            msg.writer().writeByte(idnpc);  // Ghi id của NPC
            msg.writer().writeByte(action);  // Ghi hành động ẩn hiện
            player.sendMessage(msg);  // Gửi thông điệp đến người chơi
            msg.cleanup();  // Dọn dẹp thông điệp
        } catch (Exception ex) {
            // Xử lý lỗi (nếu có)
            ex.printStackTrace();  // Hiển thị lỗi ra console
        }
    }
}
