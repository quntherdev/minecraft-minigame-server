package fr.qunther.raw_chaos;

import java.util.ArrayList;

public class PlayerBDD {
	public static void main(String[] args) {

	}
	
	
	public static boolean isPlayerTemporaryBanned(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT count(*) FROM ban WHERE uuid=? AND duration_left!=0 LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		
		return false;
	}
	
	
	public static boolean isPlayerPermanentBanned(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT count(*) FROM ban WHERE uuid=? AND duration_left=-1 LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		
		return false;
	}
	
	
	public static int getIdBan(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT id_ban FROM ban WHERE uuid=? AND duration_left!=0 LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (int)arr.get(0);
	}
	
	public static void decrementOneHourBan(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(getIdBan(uuid));

		String sql = "UPDATE ban SET duration_left = duration_left-1 WHERE uuid=? AND duration_left<>0 AND id_ban=? AND duration_left<>-1";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	public static void decrementAllOneHourBan() {
		ArrayList<Object> arguments = new ArrayList<Object>();

		String sql = "UPDATE ban SET duration_left=duration_left-1 WHERE duration_left<>0 AND ban_duration<>-1";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	public static void banPlayer(String uuid_to_ban, String uuid_banner, int ban_reason, int hours) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid_to_ban);
		arguments.add(uuid_banner);
		arguments.add(ban_reason);
		arguments.add(hours);
		arguments.add(hours);
		
		String sql = "INSERT INTO ban(id_ban, uuid, uuid_banni_par, id_ban_reason, ban_duration, date, duration_left)"
					+" VALUES ((SELECT MAX(id_ban) FROM ban)+1, ?, ?, ?, ?, current_timestamp, ?)";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	public static void unbanPlayer(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "UPDATE ban SET duration_left=0 WHERE uuid=?";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	
	
	/*
	 * 
	 * BAN INFOS
	 * 
	 */
	
	
	public static String getWhoBanned(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT nickname FROM player WHERE uuid =(SELECT uuid_banni_par FROM ban WHERE uuid=? ORDER BY date DESC LIMIT 1) ORDER BY first_join_date DESC LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (String) arr.get(0);
	}
	
	
	public static String getPlayerBanReasonTitle(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT title_ban_reason FROM ban_reason WHERE id_ban_reason=(SELECT id_ban_reason FROM ban WHERE uuid=? ORDER BY date DESC LIMIT 1)";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (String) arr.get(0);
	}
	
	
	public static int getPlayerBanRemainingHeures(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT duration_left FROM ban WHERE uuid=? ORDER BY date DESC LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (int)arr.get(0);
	}
	
	
	
	
	
	
	
	
	
	/*
	 *  MUTE
	 * 
	 * 
	 */
	

	public static boolean isPlayerTemporaryMuted(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT count(*) FROM mute WHERE uuid=? AND duration_left!=0 LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		
		return false;
	}
	
	
	
	public static boolean isPlayerPermanentMuted(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT count(*) FROM mute WHERE uuid=? AND duration_left=-1 LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		
		return false;
	}
	
	
	public static int getIdMute(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT id_mute FROM ban WHERE uuid=? AND duration_left!=0 LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (int)arr.get(0);
	}
	
	public static void decrementOneHourMute(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(getIdMute(uuid));

		String sql = "UPDATE mute SET duration_left = duration_left-1 WHERE uuid=? AND duration_left<>0 AND id_mute=? AND duration_left<>-1";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	public static void decrementAllOneHourMute() {
		ArrayList<Object> arguments = new ArrayList<Object>();

		String sql = "UPDATE mute SET duration_left=duration_left-1 WHERE duration_left<>0 AND mute_duration<>-1";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	public static void mutePlayer(String uuid_to_ban, int mute_reason, int hours, String uuid_banner) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid_to_ban);
		arguments.add(mute_reason);
		arguments.add(uuid_banner);
		arguments.add(hours);
		arguments.add(hours);
		
		String sql = "INSERT INTO mute(id_mute, uuid, id_mute_reason, uuid_muter, mute_duration, date, duration_left)"
					+" VALUES ((SELECT MAX(id_mute)+1 FROM mute), ?, ?, ?, ?, current_timestamp, ?)";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	public static void unmutePlayer(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "UPDATE mute SET duration_left=0 WHERE uuid=?";
		QuerySender.sendInsertQuery(sql,arguments);
	}
	
	
	
	/*
	 * 
	 *  MUTE INFOS
	 * 
	 */
	
	
	public static String getWhoMuted(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT nickname FROM player WHERE uuid =(SELECT uuid_muter FROM mute WHERE uuid=? ORDER BY date DESC LIMIT 1) ORDER BY first_join_date DESC LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (String) arr.get(0);
	}
	
	
	public static String getPlayerMuteReasonTitle(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT title_mute_reason FROM mute_reason WHERE id_mute_reason=(SELECT id_mute_reason FROM mute WHERE uuid=? ORDER BY date DESC LIMIT 1)";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (String) arr.get(0);
	}
	
	
	public static int getPlayerMuteRemainingHeures(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT duration_left FROM mute WHERE uuid=? ORDER BY date DESC LIMIT 1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return (int)arr.get(0);
	}
	
	
	
	
	
	
	
	/*
	 *  ============================== PLAYER INFOS ===================================
	 */
	
	public static boolean isPlayerInBDD(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);

		String sql = "SELECT count(*) FROM player WHERE UUID=?";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		
		return false;
	}
	

	public static ArrayList<Object> getPlayerInfos(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT * FROM player WHERE UUID=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static void getPlayerAllOrders(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
	}
	
	
	public static ArrayList<Object> getPlayerShopRank(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT title_rank FROM shop_rank WHERE id_rank_shop=(SELECT id_rank_shop FROM player WHERE uuid=?)";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static ArrayList<Object> getPlayerStaffRank(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT title_rank FROM staff_rank WHERE id_rank_staff=(SELECT id_rank_staff FROM player WHERE uuid=?)";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	
	public static ArrayList<Object> getTotalPlayersInBDD() {
		ArrayList<Object> arguments = new ArrayList<Object>();

		String sql = "SELECT count(*) FROM player";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql,arguments);
		
		return arr;
	}
	
	
	/*
	 *  ============================== CONNECTION ===================================
	 */
	
	public static Object[][] getConnectionInfos(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT * FROM connection_infos WHERE uuid=?";
		
		Object[][] arr = QuerySender.sendGetterALLrowsQuery(sql,arguments);
		return arr;
	}
	
	public static void insertPlayerFirstConnectionInfos(String uuid,String nickname, String ip) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(nickname);
		
		String sql = "INSERT INTO player (uuid, id_rank_shop, nickname,first_join_date,best_time_jump,raw_coins,gemmes)"
		            + "VALUES (?, 1, ?, current_timestamp, 0, 0, 0)";
		QuerySender.sendInsertQuery(sql,arguments);

		arguments.add(ip);
		insertPlayerConnectionInfos(uuid, ip);
	}
	
	public static void insertPlayerConnectionInfos(String uuid, String ip) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(ip);
		
		String sql = "INSERT INTO connection_infos (id_connection,uuid,ip_address,connection_date)"
		            + " VALUES ((SELECT MAX(id_connection)+1 FROM connection_infos), ?,?, current_timestamp)";
		
		QuerySender.sendInsertQuery(sql, arguments);
	}
	/*
	 *  ============================== RAW COINS ===================================
	 */
	
	public static ArrayList<Object> getPlayerRawCoins(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT raw_coins FROM player WHERE UUID=?";
		return QuerySender.sendGetterQuery(sql, arguments);	
	}
	
	public static void setPlayerRawCoins(String uuid, int raw_coins) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(raw_coins);
		arguments.add(uuid);
		
		String sql = "UPDATE player SET raw_coins=? WHERE uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);
	}
	
	public static void addOrRemovePlayerRawCoins(String uuid, int raw_coins) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(raw_coins);
		arguments.add(uuid);
		arguments.add(uuid);

		String sql = "UPDATE player SET raw_coins=?"
		   		+ "+(SELECT raw_coins from player WHERE uuid=?) WHERE uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);
	}
	
	/*
	 *  ============================== GEMMES ===================================
	 */
	
	public static ArrayList<Object> getPlayerGemmes(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT gemmes FROM player WHERE UUID=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	
	public static void setPlayerGemmes(String uuid, int gemmes) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(gemmes);
		arguments.add(uuid);
		
		String sql = "UPDATE player SET gemmes=? WHERE uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);
	}
	
	public static void addOrRemovePlayerGemmes(String uuid, int l) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(l);
		arguments.add(uuid);
		arguments.add(uuid);
		
		String sql = "UPDATE player SET gemmes=?+(SELECT gemmes from player WHERE uuid=?) WHERE uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);	
	}
	
	/*
	 *  ============================== RAW COINS ===================================
	 */
	
	public static void setPlayerShopRank(String uuid, int id_rank) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(id_rank);
		arguments.add(uuid);
		
		String sql = "UPDATE player SET id_rank_shop=? WHERE uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);
	}
	
	
	/*
	 *  ============================== COSMETIQUES ===================================
	 */
	
	public static Object[][] getPlayerCosmetics(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "select * from joueur_posseder_cosmetic where uuid=?";
		return QuerySender.sendGetterALLrowsQuery(sql, arguments);
	}
	
	public static ArrayList<Object> playerHasCosmetic(String uuid, int id_cosmetic) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(id_cosmetic);
		
		String sql = "select * from joueur_posseder_cosmetic where uuid=? and id_cosmetic=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static Object isPlayerCosmeticActive(String uuid, int id_cosmetic) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(id_cosmetic);
		arguments.add(uuid);
		
		String sql = "SELECT is_active FROM joueur_posseder_cosmetic WHERE uuid=? AND id_cosmetic=? AND is_active=1";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static int hasPlayerOneActiveCosmetic(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT is_active FROM joueur_posseder_cosmetic WHERE uuid=? AND is_active=1";
		if(QuerySender.sendGetterQuery(sql, arguments).get(0).toString()=="1") {
			return 1;
		}
		return 0;
	}
	
	public static void addPlayerCosmetic(String uuid, int id_cosmetic) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(id_cosmetic);
		
		String sql = "INSERT INTO joueur_posseder_cosmetic (uuid, id_cosmetic, is_active) VALUES (?, ?, 0)";
		QuerySender.sendInsertQuery(sql, arguments);
	}
	
	
	public static void removePlayerCosmetic(String uuid, int id_cosmetic) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(id_cosmetic);
		arguments.add(uuid);
		
		String sql = "DELETE FROM joueur_posseder_cosmetic WHERE id_cosmetic=? AND uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);
	}
	
	/*
	 *  ============================== BAN ===================================
	 */
	/*
	public static void setPlayerGemmes(String uuid_player_to_ban, String uuid_banner, int id_ban_reason, int ban_duration) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(gemmes);
		arguments.add(uuid);
		
		String sql = "UPDATE player SET gemmes=? WHERE uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);
	}*/
	
	public static ArrayList<Object> getBanReasonTitleFromIdReason(int id_reason) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(id_reason);
		
		String sql = "select title_ban_reason from ban_reason where id_ban_reason=?;";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	
	/*
	 *  ============================== MUTE ===================================
	 */

	/*
	 *  ============================== GAME ===================================
	 */
	
	public static ArrayList<Object> getGameBonusInfos(int bonus_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(bonus_id);		
		
		String sql = "SELECT * FROM game_bonus where id_bonus=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static boolean playerBoughtBonus(String uuid, int bonus_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(bonus_id);		
		
		String sql = "SELECT COUNT(*) FROM shop_order where uuid=? and id_bonus=?";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql, arguments);
		
		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		return false;
	}
	
	public static ArrayList<Object> getPlayerGameBonusInfos(String uuid, int id_match) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(id_match);
		
		String sql = "SELECT id_bonus FROM a_joue_parti WHERE uuid=? AND id_match=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static boolean hasPlayerTempoBonus(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT COUNT(*) from tempo_bonus where uuid=?";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql, arguments);

		if((long)arr.get(0)==1) return true;
		return false;
	}
	
	public static ArrayList<Object> getPlayerTempoBonus(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT id_bonus FROM tempo_bonus WHERE uuid=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static void removePlayerTempoBonus(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "DELETE FROM tempo_bonus WHERE uuid=?";
		QuerySender.sendInsertQuery(sql, arguments);		
	}
	
	public static void addPlayerTempoBonus(String uuid, int bonus_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(bonus_id);
		
		String sql = "INSERT INTO tempo_bonus VALUES (?, ?)";
		QuerySender.sendInsertQuery(sql, arguments);		
	}
	
	public static ArrayList<Object> getChaosBonusRawCoins(int bonus_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(bonus_id);		
		
		String sql = "SELECT raw_coins FROM game_bonus WHERE id_bonus=?";
		return QuerySender.sendGetterQuery(sql, arguments);

	}
	
	public static ArrayList<Object> getChaosBonusGemmes(int bonus_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(bonus_id);		
		
		String sql = "SELECT gemmes FROM game_bonus WHERE id_bonus=?";
		return QuerySender.sendGetterQuery(sql, arguments);

	}
	
	public static void insertPlayerOrderChaosBonus(String uuid, int bonus_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(bonus_id);
		
		String sql = "INSERT INTO shop_order (id_order, uuid, id_bonus, date) VALUES "
					+"((SELECT MAX(id_order) FROM shop_order)+1, ?, ?, current_timestamp)";
		QuerySender.sendInsertQuery(sql, arguments);
		
		setPlayerShopRank(uuid, bonus_id);
	}
	
	
	
	
	
	public static void createChaosMatch(int nb_players) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(nb_players);
		
		String sql = "INSERT INTO match_infos VALUES ((SELECT MAX(id_match)+1 FROM match_infos), 1, ?, current_timestamp)";
		QuerySender.sendInsertQuery(sql, arguments);		
	}
	
	public static void insertChaosPlayerMatch(String uuid, int id_bonus, Long id_match, int classement, int kills, int temps_survie_secondes) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(id_bonus);
		arguments.add(id_match);
		arguments.add(classement);
		arguments.add(kills);
		arguments.add(temps_survie_secondes);

		String sql = "INSERT INTO a_joue_partie VALUES (?, ?, ?, ?, ?, ?)";
		QuerySender.sendInsertQuery(sql, arguments);		
	}
	
	public static ArrayList<Object> getLastChaosMatchID() {
		ArrayList<Object> arguments = new ArrayList<Object>();

		String sql = "SELECT MAX(id_match) FROM match_infos";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	
	
	public static ArrayList<Object> getMatchHour(Long match_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(match_id);
		
		String sql = "SELECT to_char(date, 'HH24') FROM match_infos WHERE id_match=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static ArrayList<Object> getMatchMinutes(Long matchID) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(matchID);
		
		String sql = "SELECT to_char(date, 'MI') FROM match_infos WHERE id_match=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static ArrayList<Object> getMatchSeconds(Long match_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(match_id);
		
		String sql = "SELECT to_char(date, 'SS') FROM match_infos WHERE id_match=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static ArrayList<Object> getCurrentHour() {
		ArrayList<Object> arguments = new ArrayList<Object>();
		
		String sql = "SELECT TO_CHAR(current_timestamp, 'HH24')";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static ArrayList<Object> getCurrentMinutes() {
		ArrayList<Object> arguments = new ArrayList<Object>();
		
		String sql = "SELECT TO_CHAR(current_timestamp, 'MI')";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static ArrayList<Object> getCurrentSeconds() {
		ArrayList<Object> arguments = new ArrayList<Object>();
		
		String sql = "SELECT TO_CHAR(current_timestamp, 'SS')";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	/*
	 *  ============================== ORDER / RANKS ===================================
	 */
	
	public static void insertPlayerOrderGemmes(String uuid, int gemmes) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(gemmes);
		
		
		String sql = "INSERT INTO shop_order (id_order, uuid, gemmes, date) VALUES "
					+"((SELECT MAX(id_order) FROM shop_order)+1, ?, ?, current_timestamp)";
		QuerySender.sendInsertQuery(sql, arguments);
		
		addOrRemovePlayerGemmes(uuid, gemmes);
	}
	
	public static void insertPlayerOrderRawCoins(String uuid, int raw_coins) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(raw_coins);
		
		
		String sql = "INSERT INTO shop_order (id_order, uuid, raw_coins, date) VALUES "
					+"((SELECT MAX(id_order) FROM shop_order)+1, ?, ?, current_timestamp)";
		QuerySender.sendInsertQuery(sql, arguments);
		
		addOrRemovePlayerRawCoins(uuid, raw_coins);
	}
	
	public static void insertPlayerOrderShopRank(String uuid, int rank_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(rank_id);
		
		
		String sql = "INSERT INTO shop_order (id_order, uuid, id_rank_shop, date) VALUES "
					+"((SELECT MAX(id_order) FROM shop_order)+1, ?, ?, current_timestamp)";
		QuerySender.sendInsertQuery(sql, arguments);
		
		setPlayerShopRank(uuid, rank_id);
	}
	
	public static void insertPlayerOrderCosmetic(String uuid, int cosmetic_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		arguments.add(cosmetic_id);
		
		
		String sql = "INSERT INTO shop_order (id_order, uuid, id_cosmetic, date) VALUES "
					+"((SELECT MAX(id_order) FROM shop_order)+1, ?, ?, current_timestamp)";
		QuerySender.sendInsertQuery(sql, arguments);
		
		addPlayerCosmetic(uuid, cosmetic_id);
	}
	
	
	public static ArrayList<Object> getRankRawCoins(int rank_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(rank_id);		
		
		String sql = "SELECT raw_coins FROM shop_rank WHERE id_rank_shop=?";
		return QuerySender.sendGetterQuery(sql, arguments);

	}
	
	
	public static ArrayList<Object> getShopRankTitleFromID(int rank_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(rank_id);		
		
		String sql = "SELECT title_rank FROM shop_rank WHERE id_rank_shop=?";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	public static ArrayList<Object> getStaffRankTitleFromID(int rank_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(rank_id);		
		
		String sql = "SELECT title_rank FROM staff_rank WHERE id_rank_staff=?";
		return QuerySender.sendGetterQuery(sql, arguments);

	}
	
	/*
	 *  ============================== PANIER ===================================
	 */
	
	public static void insertPlayerPanier(String uuid, String product_type, int product_id) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		
		String sql = "INSERT INTO temp_order (id_temp_order)"
					+ "VALUES ((SELECT MAX(id_temp_order)+1 FROM temp_order))";
		QuerySender.sendInsertQuery(sql, arguments);

		arguments.add(uuid);
		arguments.add(product_type);
		arguments.add(product_id);
		
		String sql2 = "INSERT INTO panier_contenir (uuid, id_temp_order, product_type, product_id) VALUES "
					+"(?, (SELECT MAX(id_temp_order) from temp_order), ?, ?)";
		QuerySender.sendInsertQuery(sql2, arguments);
	}
	
	public static ArrayList<Object> getLastPlayerPanier(String uuid) {
		ArrayList<Object> arguments = new ArrayList<Object>();
		arguments.add(uuid);
		
		String sql = "SELECT * FROM panier_contenir WHERE uuid=? ORDER BY id_temp_order DESC LIMIT 1";
		return QuerySender.sendGetterQuery(sql, arguments);
	}
	
	
	
	
	/*
	 *  ============================== FRIENDS ===================================
	 */
	
	
	/*
	public static void claimChunk() {}
	public static void removeClaimChunk() {}
	public static void PlayerCreateFaction(String uuid) {}
	public static void PlayerJoinFaction(String uuid) {}
	public static void deleteFaction(String uuid) {}
	public static void getFactionInfos(String faction_name) {}
	public static void getFactionChunks(String faction_name) {}
	public static void getFactionFromChunk(int x, int y) {}
	
	public static String getPlayerFaction(String uuid) {
		String sql = "SELECT faction_name FROM appartenir_faction WHERE UUID='"+uuid+"'";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql);

		return (String) arr.get(0);
	}
	
	public static boolean isPlayerLeader(String uuid) {
		String sql = "SELECT count(*) FROM appartenir_faction WHERE UUID='"+uuid+"' AND is_leader=1";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql);

		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		return false;
	}
	
	public static boolean isChunkClaimed(int x, int y) {
		String sql = "SELECT count(*) FROM chunk WHERE x="+x+" AND y="+y+"";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql);

		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		return false;
	}

	public static boolean isPlayerInFaction(String uuid) {
		String sql = "SELECT count(*) FROM appartenir_faction WHERE UUID='"+uuid+"'";
		ArrayList<Object> arr = QuerySender.sendGetterQuery(sql);

		if(arr.get(0).toString().equals(String.valueOf("1"))) return true;
		return false;
	}*/
	
	
	
}
