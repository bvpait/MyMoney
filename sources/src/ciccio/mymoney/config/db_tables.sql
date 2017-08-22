VIEW v_mensile_default_conto
SELECT IFNULL(m.id, 0) as id, m.idConto, p.nome as nomeConto, c1.id as idCategoria, c1.nome as nomeCategoria,
	   c.id as idSottoCategoria, c.nome as nomeSottoCategoria, 
	   IFNULL(m.idBeneficiario, -1) as idBeneficiario, b.nome as nomeBeneficiario,
	   m.importo, m.tipo, m.note, m.idTag
  FROM categoria c
  	   left join categoria c1 on c.idPadre = c1.id
  	   left join mensile_default m on m.idCategoria = c.id
  	   left join conto p on m.idConto = p.id
  	   left join beneficiario b on m.idBeneficiario = b.id
  	   left join tag t on m.idTag = t.id
 WHERE c.idPadre > 0

VIEW v_bilancio_mensile_conto
SELECT t.id, t.idConto, p.nome as nomeConto, t.anno, t.mese, t.idContoTrasferimento,
	   t.idCategoria, c.nome as nomeCategoria,
	   t.idSottoCategoria, c1.nome as nomeSottoCategoria, 
	   IFNULL(t.idBeneficiario, -1) as idBeneficiario, b.nome as nomeBeneficiario,
	   t.exp, t.importo, t.tipo, t.note, t.idTag 
  FROM transazione t
  	   left join categoria c on t.idCategoria = c.id
  	   left join categoria c1 on t.idSottoCategoria = c1.id
  	   left join conto p on t.idConto = p.id
  	   left join beneficiario b on t.idBeneficiario = b.id
  	   left join tag g on t.idTag = g.id


VIEW v_report_conto_mensile
SELECT idConto, anno, mese,
	sum(IF(tipo = 1, importo, 0)) as uscite,
	sum(IF(tipo = 2, importo, 0)) as entrate,
	sum(IF(tipo = 3, importo, 0)) as trasf_uscite,
	sum(IF(tipo = 4, importo, 0)) as trasf_entrate
  FROM transazione
 GROUP BY idConto, anno, mese
 
VIEW v_report_conto_anno
SELECT idConto, anno, 
	sum(IF(tipo = 1, importo, 0)) as uscite,
	sum(IF(tipo = 2, importo, 0)) as entrate,
	sum(IF(tipo = 3, importo, 0)) as trasf_uscite,
	sum(IF(tipo = 4, importo, 0)) as trasf_entrate
  FROM transazione
 GROUP BY idConto, anno
 
VIEW v_report_conto
SELECT idConto,
	sum(IF(tipo = 1, importo, 0)) as uscite,
	sum(IF(tipo = 2, importo, 0)) as entrate,
	sum(IF(tipo = 3, importo, 0)) as trasf_uscite,
	sum(IF(tipo = 4, importo, 0)) as trasf_entrate
  FROM transazione
 GROUP BY idConto
	
 
 

 
	
	<select id="ElencoIstanzeAperte" resultMap="istanzaResultMap" parameterType="it.finanze.ag_dogane.dogane.dogana.opd.istanze.bean.IstanzaRicerca">
		SELECT istanza.*, Upper(stato.descrizione) as descrizioneStato, p.*
		FROM ip_istanza istanza, ip_tipo_stato stato, ip_presentatore_istanza p
		WHERE istanza.cod_stato = stato.codice
			AND istanza.id = p.id_istanza
			AND stato.stato_finale = 0
		<if test="!operatoreEconomico">
			<if test="codiceUfficio > 0">
			AND istanza.bol_cod_uff_dog = #{codiceUfficio}
				<if test="codiceFiscale != null">
			AND istanza.utente_incaricato IS NULL
				</if>
			</if>
			<if test="codiceUfficio == 0">
			AND istanza.utente_incaricato = #{codiceFiscale}
			</if>
		</if>
		<if test="operatoreEconomico">
			AND p.piva_ditta in
			<foreach item="item" index="index" collection="deleganti"  open="(" separator="," close=")">
	        	#{item.codiceFiscale}
	    	</foreach>
		</if>
		ORDER BY istanza.data_scadenza
	</select>
	
	<select id="ElencoNotificheDaLeggere" resultMap="notificaResultMap" parameterType="it.finanze.ag_dogane.dogane.dogana.opd.istanze.bean.UserData">
  		SELECT n.*, i.protocollo
		FROM ip_notifica_istanza n 
			 LEFT JOIN ip_istanza i on n.id_istanza = i.id
		<if test="operatoreEconomico">
			 LEFT JOIN ip_presentatore_istanza p on p.id_istanza=i.id
		</if>
		WHERE data_lettura IS NULL
		<if test="!operatoreEconomico">
			AND i.bol_cod_uff_dog = #{ufficio.codUfficio}
			AND i.utente_incaricato = #{userName}
			AND n.destinatario='FD'
		</if>
		<if test="operatoreEconomico">			
			AND n.destinatario='OE'
			AND p.piva_ditta in
			<foreach item="item" index="index" collection="deleganti"  open="(" separator="," close=")">
	        	#{item.codiceFiscale}
	    	</foreach>
		</if>
			AND n.utente_ins != #{userName}  			 
  			order by data_invio	desc
	</select>
	
	<select id="ElencoModificheIstanza" resultMap="modificaResultMap" parameterType="int">
		SELECT m.*, n.etichetta
        FROM ip_modifica_istanza m
          LEFT OUTER JOIN ip_modifica_nome_campo n
          ON (CASE WHEN m.prog_singolo>0 THEN 'singolo.' || m.nome_campo ELSE m.nome_campo END) = n.nome_campo
        WHERE m.id_istanza = #{idIstanza}
	</select>

	<select id="ReportHomePage" resultMap="reportLettoreCentraleResultMap" parameterType="int">
		SELECT *
		  FROM TABLE(PKG_IP_ISTANZA.report_anno(#{anno}))
	</select>
	
	<select id="IstanzaByPresentatore" parameterType="map" resultMap="istanzaResultMap">
		SELECT
			istanza.*,
			Upper(stato.descrizione) as descrizioneStato,
			stato.stato_finale as statoFinale,
			m.descrizione as descrMotivazione,
			e.descrizione as descrizioneEsito,			
			(SELECT note FROM ip_stato_istanza WHERE id = (SELECT min(id) FROM ip_stato_istanza WHERE id_istanza = ${id})) AS primaNota
		FROM ip_istanza istanza 
			 left join ip_tipo_stato stato on stato.codice=istanza.cod_stato
			 left join IP_TIPO_MOTIVAZIONE_ISTANZA m on m.codice=istanza.cod_motivo_istanza and istanza.data_presentazione between m.data_ini_val and m.data_fine_val
			 left join IP_TIPO_ESITO e on e.codice=istanza.esito and istanza.data_presentazione between e.data_ini_val and e.data_fine_val
			 left join ip_presentatore_istanza p on p.id_istanza=istanza.id
		WHERE id = ${id}
		<if test="user.operatoreEconomico">
			AND p.piva_ditta in
			<foreach item="item" index="index" collection="user.deleganti"  open="(" separator="," close=")">
	        	#{item.codiceFiscale}
	    	</foreach>
	    </if>	  
	</select>
	
	<select id="RicercaIstanze" parameterType="IstanzaRicerca" resultMap="istanzaResultMap">
		SELECT 
			istanza.*,
			Upper(stato.descrizione) as descrizioneStato, 
			stato.stato_finale as statoFinale,
			m.descrizione as descrMotivazione,
			e.descrizione as descrizioneEsito,
			p.*
		FROM ip_istanza istanza left join ip_tipo_stato stato on stato.codice=istanza.cod_stato
			 left join IP_TIPO_MOTIVAZIONE_ISTANZA m on m.codice=istanza.cod_motivo_istanza and istanza.data_presentazione between m.data_ini_val and m.data_fine_val
			 left join IP_TIPO_ESITO e on e.codice=istanza.esito and istanza.data_presentazione between e.data_ini_val and e.data_fine_val
			 left join ip_presentatore_istanza p on p.id_istanza=istanza.id
		WHERE 1>0
				
		<if test="codiceFiscale != null">
			AND p.piva_ditta = #{codiceFiscale}
		</if>
		<if test="codiceFiscale == null">
			<if test="operatoreEconomico">
			AND p.piva_ditta in
			<foreach item="item" index="index" collection="deleganti"  open="(" separator="," close=")">
	        	#{item.codiceFiscale}
	    	</foreach>
	    	</if>	
    	</if>			
		<if test="protocollo != null">
			AND istanza.protocollo = #{protocollo}
		</if>
		<if test="aperta > 0">
			AND stato.stato_finale = 0
		</if>
		<if test="stato > 0">
			AND istanza.cod_stato = #{stato}
		</if>
		<if test="dataPresentazioneDA != null">
			AND Trunc(istanza.data_presentazione) >= Trunc(#{dataPresentazioneDA})
		</if>
		<if test="dataPresentazioneA != null">
			AND Trunc(istanza.data_presentazione) <![CDATA[<]]>= Trunc(#{dataPresentazioneA})
		</if>
		<if test="estremiDichiarazioneDoganale != null">
			<if test="estremiDichiarazioneDoganale.codiceUfficio > 0">
				AND istanza.bol_cod_uff_dog = #{estremiDichiarazioneDoganale.codiceUfficio}
			</if>
			<if test="estremiDichiarazioneDoganale.codiceRegistro != null">
				AND istanza.bol_cod_registro = #{estremiDichiarazioneDoganale.codiceRegistro}
			</if>
			<if test="estremiDichiarazioneDoganale.numeroRegistrazione > 0">
				AND istanza.bol_num_reg = #{estremiDichiarazioneDoganale.numeroRegistrazione}
			</if>
			<if test="estremiDichiarazioneDoganale.cinControllo != null">
				AND istanza.bol_cin_bol = #{estremiDichiarazioneDoganale.cinControllo}
			</if>
			<if test="estremiDichiarazioneDoganale.dataRegistrazione != null">
				AND Trunc(istanza.bol_data_reg) = Trunc(#{estremiDichiarazioneDoganale.dataRegistrazione})
			</if>
		</if>
		order by istanza.DATA_SCADENZA, istanza.DATA_PRESENTAZIONE
	</select>
	