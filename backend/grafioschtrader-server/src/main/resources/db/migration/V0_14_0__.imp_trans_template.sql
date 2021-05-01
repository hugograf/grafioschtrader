DELETE FROM imp_trans_template WHERE id_trans_imp_template <= 38;
ALTER TABLE `imp_trans_template` ADD IF NOT EXISTS `template_category` TINYINT(2) NOT NULL DEFAULT '0' AFTER `template_purpose`; 
ALTER TABLE `imp_trans_template` CHANGE `template_format_type` `template_format_type` TINYINT(2) NOT NULL; 

ALTER TABLE `imp_trans_template` ADD UNIQUE `UNIQUE_imp_template` (`id_trans_imp_platform`, `template_format_type`, `template_category`, `template_language`, `valid_since`); 
UPDATE imp_trans_template SET template_category = RAND()*99 WHERE id_trans_imp_template >= 39; 

INSERT INTO `imp_trans_template` (`id_trans_imp_template`, `id_trans_imp_platform`, `template_format_type`, `template_purpose`, `template_category`, `template_as_txt`, `valid_since`, `template_language`, `created_by`, `creation_time`, `last_modified_by`, `last_modified_time`, `version`) VALUES
(1, 1, 0, 'Kauf und Verkauf Wertpapier (Wiederholung units)', 0, 'Gland, {datetime|P|N}\n(?:Börsengeschäft:|Börsentransaktion:) {transType|P|N} Unsere Referenz: 49534746 \nGemäss Ihrem Kaufauftrag vom 28.01.2013 haben wir folgende Transaktionen vorgenommen:\nTitel Ort der Ausführung\nISHARES $ CORP BND ISIN: {isin|P} SIX Swiss Exchange\nNKN: 1613957\nAnzahl Preis Betrag\n{units|PL|R} {quotation} {cac} 8’000.00\nTotal USD 8’250.00\nKommission Swissquote Bank AG USD {tc1|SL|N|O}\n[Abgabe (Eidg. Stempelsteuer)|Eidgenössische Stempelsteuer] USD {tt1|SL|N}\nBörsengebühren USD {tc2|SL|N}\nZu Ihren Lasten USD {ta|SL|N}\n[END]\ntransType=ACCUMULATE|Kauf\ntransType=REDUCE|Verkauf\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2011-10-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-02-28 15:17:37', 7),
(2, 1, 0, 'Kauf und Verkauf Wertpapier  (Wiederholung units)', 0, 'Börsengeschäft                    REF : BO/28082356\nIhr {transType|P|N} vom {datetime|P|N}                                                      \nAbschlussort : SIX Swiss Exchange\nBEZEICHNUNG                                      VALOREN NR          \nISIN      \nDB X-TR MSEM                                     3\'614\'480\n{isin|P|NL}\nANZAHL                KURS                       BETRAG\n-{units|Pc|Nc|PL|R}-  {cac} {quotation}                  CHF          1.00\nAusländische Taxen USD {tt2|SL|N|O}\nCourtage SQB               CHF               {tc1|SL|N|O}\nEidg. Stempel              CHF               {tt1|SL|N}\nBörsengebühren             CHF               {tc2|SL|N|O}\n----------------------\nZU IHREN GUNSTEN CHF          {ta|SL|N}\n======================\nValuta      02.11.2010\n[END]\ntransType=ACCUMULATE|Kauf\ntransType=REDUCE|Verkauf\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n\n', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-02-28 15:17:19', 6),
(3, 2, 0, 'Kauf und Verkauf Wertpapier', 0, 'IHR {transType|P|N} VOM {datetime|P|Nc}: SWX USD\n{units|P|PL}  UNITS -A USD- CAPITALISATION\nVALOR {sf1|P|N}\nZU {cin|P|PL} {quotation|SL|N|PL}\nBRUTTO                                        USD         24,750.00\nSTEUERN, MARKTGEBUEHREN, FREMDE SPESEN ETC.   USD  {tc2|SL|N|O}\nBRUTTO                                        USD         24,752.49\nKURS      {cex|P|O}                           CHF         31,151.00\nCOURTAGE                                      {cac|P|SL}  {tc1|SL|N}\nEIDG. UMSATZABGABE + SWX/EBK-GEBUEHR          CHF         {tt1|SL|N}\nB E R N                                         VALUTA 27.02.2007   CHF        {ta|SL|N}\n[END]\ntransType=ACCUMULATE|Kauf\ntransType=ACCUMULATE|KAUF\ntransType=REDUCE|Verkauf\ndateFormat=dd.MM.yyyy\n', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-02-28 15:19:21', 3),
(4, 2, 0, 'Kauf und Verkauf Wertpapier', 0, 'Auftragsnummer AUF8909899\nAuftragsdatum 10.08.2017 Thun, {datetime|SL|N}\nBörsenabrechnung - {transType|SL|P}  - Teilausführung\nWir haben für Sie am 10.08.2017 verkauft.\nBörsenplatz: SIX Swiss Exchange\nAnteile -H CHF hedged-\nRaiffeisen ETF - Solid Gold Ounces\nValor: 13403486\nISIN: {isin|P|N}\n{units|PL|R} zu (?:...) {quotation} 15:14:45\nTotal Kurswert CHF 20\'216.00\nEidg. Umsatzabgabe CHF  {tt1|SL|N}\nCourtage CHF {tc1|SL|N}\nAusführungsgebühr Börse CHF {tc2|SL|N|O}\nNetto  {cac|SL|P} {ta|SL|N}\n[END]\ntransType=ACCUMULATE|Kauf\ntransType=REDUCE|Verkauf\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n\n', '2015-08-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-02-28 15:19:35', 6),
(5, 2, 0, 'Dividende', 13, 'Am {datetime|P|N} wurde die {transType|P|N} zahlbar auf\n(?:.*Ex.Datum.*) {exdiv|P|N|O}\nProShares Short QQQ Zahlbar Datum: 02.07.2019\nValor: 25792502\nISIN: {isin|P|N}\nBestand: {units|P|N|SL} zu {cin|P|SL} {quotation|SL|N}\nBrutto (200 * USD 0.1838) USD 36.76\n(?:.*steuer.*)  {tt1|P|PL|N|O}\n(?:.*zu.*steuer.*)  {tt2|P|NL|N|O}\nNetto {cac|P|SL} {ta|SL|N}\nUnsere Gutschrift erfolgt auf Konto 00 .0.000.000.00 mit Valuta 28.11.2016.\n[END]\ntransType=DIVIDEND|Dividende\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2015-08-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:46:23', 26),
(6, 3, 0, 'Ertrag Yellowtrade', 14, '{transType|P|N|NL}\n[SEITE|3002]\nWIR ERKENNEN SIE, EINGANG VORBEHALTEN, MIT FOLGENDEN TITELERTRAEGEN.\n{units|P|PL} ACT GBP SVN VALOREN-NR. 1.102.657\nGLAXOSMITHKLINE PLC ISIN {isin|P|N}\nIM DEPOT: DEUTSCHE BK FRANKFU.\nEX-DATUM : 10.05.2006\nVERFALLSDATUM : 06.07.2006\nCOUPON : 3\nEINHEITSPREIS TOTALBETRAEGE\nANGEKUENDIGT. ERTRAG  0,11 44,00\nSTEUERGUTHABEN 0,012225 {tt1|SL|N|O}\nBRUTTOERTRAG GBP {quotation|SL} GBP 1,00\n[STEUERGUTHABEN|STEUER] 00%  {tt1|SL|Nc|O}-\nNETTOERTRAG 44,00\nNETTOBETRAG {cin|P|NL} 44,00\nDIESEN BETRAG SCHREIBEN WIR DEM KONTO GUT\nR 0000.00.00 VALUTA : {datetime|PL} {cac|PL} {ta|PL|N}\nKURS : {cex|SL|N|O}\nSTEUERRUECKFORDERUNG\nSTEUER.GUTHABENVERR. GBP 4,89\nSTEUERVERRECHNUNG CHF 10,95\n[END]\ntransType=DIVIDEND|ERTRAGSABRECHNUNG\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2007-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:28:19', 8),
(7, 3, 0, 'Kauf und Verkauf Wertpapier', 0, 'Bern, {datetime|P|N}\nBörsentransaktion: {transType|P|N}\nUnsere Referenz: 142494149 \nGemäss Ihrem Kaufauftrag vom 08.03.2018 haben wir folgende Transaktionen vorgenommen:\nTitel Ort der Ausführung\n2.20 BALIFE 17-48 ISIN: {isin|P} SIX Swiss Exchange\nNKN: 37961100\nAnzahl Preis Betrag\n{units|PL|R} {quotation} {per|O} {cin} 28’450.00\nCHF\nMarchzinsen {ac|SL|N|O}\nTotal CHF 5\'068.60\nKommission CHF {tc1|SL|N|O}\nAbgabe (Eidg. Stempelsteuer) CHF {tt1|SL|N}\nBörsengebühren CHF {tc2|SL|N|O}\nZu Ihren Lasten {cac|SL} {ta|SL|N}\n[END]\ntransType=ACCUMULATE|Kauf\ntransType=REDUCE|Verkauf\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-02-28 15:22:58', 5),
(8, 3, 0, 'Rückkaufangebot', 9, 'Bern, 30.01.2018\n{transType|PL|P|N}\nUnsere Referenz: 140209352 \nWir haben folgende Transaktion auf Ihrem Konto vorgenommen:\nTitel\nOriginal Original Anzahl\nISIN: {isin|P|N}\n2.375 REPOWER 10-22 {units|PL|N}\nNKN: 10915272\nBewegung Ratio Anzahl\nhaben wir Ihrem Konto den folgenden Betrag gutgeschrieben:\nAusführungsdatum {datetime|P|N}\nAnzahl {units|P|N}\nPreis {quotation|P|SL} %\nTotal {cac|P|SL} {ta|SL|N}\n[END]\ntransType=REDUCE|Rückkaufangebot\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n\n', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:13:54', 5),
(9, 3, 0, 'Zins für Anleihen', 16, 'Bern, {datetime|P|N}\n{transType|PL|P}\nUnsere Referenz: 139280138 \nIm Hinblick auf folgenden Titel:\nTitel\nBezeichnung Anzahl\nISIN: {isin|P|N}\n4 ALFA 13-18 20\'000\nAusführungsdatum 16.01.2018\nValutadatum\nNominal {units|P|N}\nZins {quotation|P|SL} {per|N|SL}\nBetrag {cin|P} 41.40\nWechselkurs {cex|P|N|O}\nVerrechnungssteuer 35% (CH) CHF {tt1|SL|N|O}\nTotal {cac|P|SL} {ta|SL|N}\n[END]\ntransType=DIVIDEND|Zins\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\notherFlagOptions=BOND_QUATION_CORRECTION\n', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:00:16', 25),
(10, 1, 0, 'Dividende alt (Wiederhlolung units/...)', 13, 'KUNDE : 000\'000 HANS MUSTER Gland, {datetime|P|N}\n{transType|P|NL} REF : CN/90057162\nBEZEICHNUNG EX TAG VALOREN NR\nXmtch on (CH 29.03.2010 1\'985\'280\n{isin|NL|P|N}\nANZAHL DIVIDENDE BETRAG\n-{units|Pc|Nc|PL|R}-  {quotation} {cac}            \nVerrechnungssteuer 35.00 % CHF {tt1|SL|Nc|O}-\n----------------------\nZU IHREN GUNSTEN CHF {ta|SL|N}\n======================\nValuta 01.04.2010\n[END]\r\ntemplatePurpose=Dividende alt (Wiederhlolung units/...)\ntransType=DIVIDEND|Dividende\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n\n', '2010-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 09:19:09', 7),
(11, 1, 0, 'Dividende', 14, 'Gland, {datetime|P|N}\n{transType|P|N} Unsere Referenz: 66622789 \nIm Hinblick auf folgenden Titel:\nTitel\nISHARES $ CORP BND ISIN: {isin|P} 490\nNKN: 1613957\nWir haben Ihrem Konto den folgenden Betrag gutgeschrieben: \nKontonummer 000000\nAusführungsdatum 21.05.2014\nValutadatum 11.06.2014\nAnzahl {units|P|N}\nDividende {quotation|P} USD\nBetrag {cac|P|PL} 416.11\nVerrechnungssteuer 35% (CH) CHF {tt1|SL|N|O}\nTotal USD {ta|SL|N}\n[END]\r\ntemplatePurpose=Dividende\ntransType=DIVIDEND|Dividende\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n\n', '2011-10-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 09:19:09', 14),
(12, 2, 0, 'Dividende', 13, 'Am {datetime|P|N} wurde die {transType|P|N} zahlbar auf \nNamen-Aktie Ex Datum: 29.03.2011 \nCisco Systems Inc Zahlbar Datum: 20.04.2011 \nValor: 918546 \nISIN: {isin|P|N} \nBestand: {units|P|N} zu {cin|P} {quotation|SL|N} \nBrutto (1 * USD 0.06) USD 0.06 \n15% Nicht rückforderbare Steuern (USD 1.00) USD {tt1|Nc|PL}-\n15% zusätzlicher Steuerrückbehalt USA (CHF 1.00) USD {tt2|Nc|NL}-\nNetto {cac|P} 105.00 \n[END]\ntransType=DIVIDEND|Dividende\ndateFormat=dd.MM.yyyy\n', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:45:44', 3),
(13, 1, 0, 'Sell (Wiederholung units)', 6, 'Gland, {datetime|P|N}\nStock-Exchange Transaction: {transType|P|N} Our reference: 119126920 \nIn accordance with your sell order of 25.01.2017 we have carried out the following transactions:\nSecurity Place of execution\nDB X-TR MSCI EM ISIN: {isin|P} SIX Swiss Exchange\nNKN: 3067289\nQuantity Price Amount\n{units|PL|R} {quotation} {cac} 1\'000.00\nTotal USD 1\'000.00\nCommission Swissquote Bank Ltd USD {tc1|SL|N}\nTax (Federal stamp duty) USD {tt1|SL|N}\nStock exchange fee USD {tc2|SL|N}\nTo your credit USD {ta|SL|N}\n[END]\ntransType=ACCUMULATE|Buy\ntransType=REDUCE|Sell\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2011-10-01', 'en', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:13:15', 6),
(14, 3, 0, 'Verkauf (ohne Wiederholung von units/quotation)', 6, 'ABRECHNUNG {transType|P|N}\nSEITE 1\nDATUM DER TRANSAKTION : {datetime|Pc|N} BOERSE : XETRA\nIHR(E) KASSAVERKAUF 1.450\nVALOREN-NR. 000,485,864 ACT \"A\" EUR 2\nISIN {isin|P} ALCATEL\n{units|P|N} ZUM KURS VON {quotation|P|N}\nBRUTTOBETRAG EUR 14.848,00\nCOURTAGE SCHWEIZ EUR {tc1|SL|N}\nBOERSE GEBUEHR EUR {tc2|SL|N|O}\nEIDG. STEMPEL EUR {tt1|SL|N}\nBETRAG, DEN WIR DEM KONTO GUTSCHREIBEN {cac|SL|P} {ta|SL|N}\nEUR E 5116.67.03 GRAF HUGO\nVALUTA : 27.10.2006\n[END]\ntransType=REDUCE|BOERSENVERKAUF\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:53:40', 5),
(15, 3, 0, 'Kapitalgewinn', 15, 'Bern, 08.09.2017\n{transType|P|N|NL}\nUnsere Referenz: 130663278 \nIm Hinblick auf folgenden Titel:\nTitel\nBezeichnung Anzahl\nISIN: {isin|P|N}\nUBSETF MSCI Switzerland CHF 195\nNKN: 22627424\nhaben wir Ihrem Konto den folgenden Betrag gutgeschrieben:\nKontonummer 86961700\nAusführungsdatum 06.09.2017\nValutadatum {datetime|SL|N}\nAnzahl {units|P|N}\nKapitalgewinn {quotation|P} CHF\nBetrag CHF 11.70\nTotal {cac|SL} {ta|SL|N}\n[END]\ntransType=DIVIDEND|Kapitalgewinn\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:01:27', 5),
(16, 3, 0, 'Kauf und Verkauf Yellowtrade Anleihe', 2, 'IHR(E) {transType|P|N}\nCHF 30.000 1.625 % OBL CHF 2014 - 15.10.2024 (1) VALOREN-NR.: 025,359,276\nIMPLENIA AG, DIETLIKON ISIN : {isin|P|N|NL}\nDATUM DER TRANSAKTION : {datetime|P|N|SL}\nBOERSE : ELEKTRONISCHE BOERSE SCHWEIZ\n{cin|P|PL|R} {units|PL|N} ZUM KURS VON {quotation|P|PL} {per|PL} CHF 1.000,00\n+ ANGEFALLENE ZINSEN WAEHREND 12 TAGE CHF {ac|SL|N|O}\nD.H. VOM 15.10.2014 BIS AM 27.10.2014 360 / 360\nBRUTTOBETRAG CHF 29.401,25\nCOURTAGE SCHWEIZ CHF {tc1|SL|N}\nSEPARATE AUSFUEHRUNGSGEBUEHR CHF {tc2|SL|N|O}\nEIDG. STEMPEL CHF {tt1|SL|N|O}\nBETRAG, DEN WIR DEM KONTO BELASTEN {cac|SL} {ta|SL|N}\n[END]\ntransType=ACCUMULATE|KAUF,KASSAKAUF\ntransType=REDUCE|VERKAUF,KASSAVERKAUF\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:51:07', 15),
(17, 3, 0, 'Zins für Anleihen', 15, 'FOLGENDE {transType|P|N} WURDEN IHREM KONTO GUTGESCHRIEBEN, EINGANG\nVORBEHALTEN.\nCHF {units|PL} 2 % OBL CHF 2013 - 9.6.2022 (1) VALOREN-NR. 22.853.145\nHOLCIM LTD, RAPPERSWIL-JONA ISIN {isin|P|N}\nIM DEPOT: SIX SIS AG.\nVERFALLSDATUM : 09.06.2014 EX-DATUM : 04.06.2014 COUPON : 09.06.2014\nZINSSATZ : {quotation|P|SL} {per|N|SL}\nZINSSATZ / EINHEITSPREIS BETRAEGE\nBRUTTOERTRAG CHF 450,00\nSTEUER 35 % {tt1|SL|N|O}\nNETTOERTRAG 292,50\nNETTOBETRAG {cac|P|SL} 292,50\nDIESEN BETRAG SCHREIBEN WIR DEM KONTO GUT : R 5116.67.01 CHF {ta|SL|N}\nVALUTA : {datetime|P|N}\nSTEUERRUECKFORDERUNG\nSTEUERABZUG CHF 157,50\nDEPOTBANK BERN, DEN 10. JUNI 2014\n[END]\ntransType=DIVIDEND|WERTPAPIERERTRAEGE\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2007-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:58:51', 4),
(18, 3, 0, 'Kassakauf Yellowtrade Aktie/ETF', 3, 'DATUM DER TRANSAKTION : {datetime|P|N} BOERSE : XETRA\nIHR(E)  {transType|P|SL} 350\nVALOREN-NR. 001,647,069 ACT USD 0.01\nISIN {isin|P} DELL INC\n{units|P|N} ZUM KURS VON {quotation|P|N}\nBRUTTOBETRAG EUR 6.275,50\nCOURTAGE SCHWEIZ EUR {tc1|SL|N}\nBOERSE GEBUEHR CHF {tc2|SL|N|O}\nEIDG. STEMPEL EUR {tt1|SL|N}\nZUM KURS VON {cex|P|N|O}\nBETRAG, DEN WIR DEM KONTO BELASTEN {cac|P|SL} {ta|SL|N}\nEUR E 5116.67.03 GRAF HUGO\nVALUTA : 04.10.2006\n[END]\ntransType=ACCUMULATE|KASSAKAUF\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:47:43', 7),
(19, 3, 0, 'Wertpapierertrag', 13, 'FOLGENDE {transType|P|N} WURDEN IHREM KONTO GUTGESCHRIEBEN, EINGANG\nVORBEHALTEN.\n{units|P|PL} ACT GBP 0.25 VALOREN-NR. 1.102.657\nGLAXOSMITHKLINE PLC ISIN {isin|P|N}\nIM DEPOT: DEUTSCHE BK FRANKFU.\nVERFALLSDATUM : 04.01.2007 EX-DATUM : 01.11.2006 COUPON : 04.01.2007\nZINSSATZ / EINHEITSPREIS BETRAEGE\nANGEKUENDIGT. ERTRAG 0,12 48,00\nSTEUERGUTHABEN 0,013325 5,33\nBRUTTOERTRAG {cin|P} {quotation|SL} GBP 53,33\n[STEUER|STEUERGUTHABEN] {tt1|SL|N|O}\nNETTOERTRAG 48,00\nNETTOBETRAG GBP 48,00\nDIESEN BETRAG SCHREIBEN WIR DEM KONTO GUT : R 5116.67.01 {cac|SL} {ta||N|SL}\nVALUTA : {datetime|SL|N}\nKURS : {cex|P|N|O}\nSTEUERRUECKFORDERUNG\nSTEUER.GUTHABENVERR. GBP 5,33\nSTEUERVERRECHNUNG CHF 12,60\n[END]\ntransType=DIVIDEND|WERTPAPIERERTRAEGE\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2007-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:54:49', 3),
(20, 3, 0, 'Kauf und Verkauf Yellowtrade Aktien/ETF', 1, 'IHR(E) {transType|P|N}\n2 ACT GBP 0.25 VALOREN-NR.: 001,102,657\nGLAXOSMITHKLINE PLC ISIN : {isin|P|N|NL}\nDATUM DER TRANSAKTION : {datetime|P|N}\nBOERSE : XETRA\n{units|R|P|PL} ZUM KURS VON  {quotation}\nBRUTTOBETRAG {cin|P|SL} 44,80\nCOURTAGE SCHWEIZ EUR {tc1|SL|N}\nSEPARATE AUSFUEHRUNGSGEBUEHR CHF {tc2|SL|N|O}\nEIDG. STEMPEL EUR {tt1|SL|N}\nZU KURS VON {cex|P|N|O}\nBETRAG, DEN WIR DEM KONTO (?:GUTSCHREIBEN|BELASTEN) {cac|P|SL} {ta|SL|N}\nEUR E 5116.67.03 GRAF HUGO\nVALUTA : 19.02.2007\n[END]\ntransType=ACCUMULATE|KAUF,KASSAKAUF\ntransType=REDUCE|VERKAUF,KASSAVERKAUF\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:16:29', 6),
(21, 3, 0, 'Rückzahlung Anleihe', 10, 'Bern, 30.01.2018\n{transType|PL|P|N}\nUnsere Referenz: 140347410 \nIm Hinblick auf folgenden Titel:\nTitel\nBezeichnung Anzahl\nISIN: {isin|P|N}\nResidual Debt 10.05% 60\'627\nNKN: 32105978\nhaben wir Ihrem Konto den folgenden Betrag gutgeschrieben:\nKontonummer 86961700\nAusführungsdatum {datetime|P|N}\nValutadatum 29.01.2018\nAnzahl {units|P|N}\nPreis {quotation|P|SL} {per|SL|N}\nBetrag {cin|P} 41.40\nWechselkurs {cex|P|N|O}\nTotal {cac|P|SL} {ta|SL|N}\nBitte prüfen Sie dieses Dokument und benachrichtigen Sie uns bei Unstimmigkeiten innert Monatsfrist.\n[END]\ntransType=REDUCE|Rückzahlung\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:14:37', 4),
(22, 3, 0, 'Rückzahlung Anleihe', 10, 'WIR ERKENNEN SIE, EINGANG VORBEHALTEN, MIT FOLGENDEN {transType|P|N} TITELN.\n{cin|PL|P} {units|PL} 2 % NOTES CHF 2011 - 23.12.2015 (1) EMT VALOREN-NR 13.107.842\nENEL FINANCE INTERNATIONAL NV ISIN {isin|P|N}\nBEI: SIX SIS AG.\nVERFALLSDATUM 23.12.2015\nRUECKZAHLUNGSPREIS  {quotation|P|SL} {per|SL|N}\nRUECKZAHLUNG TOTALBETRAEGE\nKAPITALRUECKZAHLUNG CHF 50 000,00\nNETTOBETRAG CHF 50 000,00\nDIESE TITEL WERDEN IHREM DEPOT ENTNOMMEN\nDIESEN BETRAG SCHREIBEN WIR DEM KONTO GUT R 5116.67.01 {cac|SL} {ta|SL|N}\nVALUTA : {datetime|P|N}\n[END]\ntransType=REDUCE|RUECKZAHLBAREN\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:52:27', 4),
(23, 3, 0, 'Dividende für Aktien/ETF', 13, 'Bern, 25.04.2018\n{transType|PL|P|N}\nUnsere Referenz: 145360471 \nIm Hinblick auf folgenden Titel:\nTitel\nBezeichnung Anzahl\nISIN: {isin|P|N}\niShares Global HY Corp Bnd CHF 650\nNKN: 22134231\nhaben wir Ihrem Konto den folgenden Betrag gutgeschrieben:\nKontonummer 86961700\nAusführungsdatum {datetime|P|N}\nValutadatum 25.04.2018\nAnzahl {units|P|N}\nDividende {quotation|P|SL}  CHF\nBetrag CHF 1\'649.31\n[Quellensteuer|Verrechnungssteuer] 35% (CH) CHF {tt1|SL|N|O}\nTotal {cac|P|SL} {ta|SL|N}\n[END]\ntransType=DIVIDEND|Dividende\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 06:59:50', 6),
(24, 3, 0, 'Kassakauf Yellowtrade Anleihe', 4, 'DATUM DER TRANSAKTION : {datetime|P|N} BOERSE : ELEKTRONISCHE BOERSE SCHWEIZ\nIHR(E) {transType|P|SL} EUR 5.000\nVALOREN-NR. 000,715,243 4.125 % OBL EUR 1999 - 30.4.2009 (1)\nISIN  {isin|P} LANDWIRTSCHAFTLICHE RENTENBANK\n{cin|P|PL} {units|N|PL} ZUM KURS VON {quotation|P|PL} {per|PL} EUR 5.049,50\n+ ANGEFALLENE ZINSEN WAEHREND 219 TAGE EUR {ac|SL|N}\nD.H. VOM 30.04.2006 BIS AM 05.12.2006 366 / 366\nBRUTTOBETRAG EUR 5.173,25\nCOURTAGE SCHWEIZ EUR {tc1|SL|N}\nEIDG. STEMPEL EUR {tt1|SL|N}\nZUM KURS VON {cex|P|N|O}\nBETRAG, DEN WIR DEM KONTO BELASTEN {cac|P|SL} {ta|SL|N}\n[END]\ntransType=ACCUMULATE|KASSAKAUF\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2000-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-29 18:48:07', 4),
(25, 3, 0, 'Dividends for Stock/ETF in english', 13, 'Bern, 31.10.2017\n{transType|PL|P|N}\nOur reference: 133508877 \nRegarding the following security:\nSecurity\nDescription Quantity\nISIN: {isin|P|N}\niShares Global HY Corp Bnd CHF 650\nNKN: 22134231\nWe have credited the following amount to your account:\nAccount number 86961700\nExecution date {datetime|P|N}\nValue date 31.10.2017\nQuantity {units|P|N}\nDividend {quotation|P|SL} CHF\nAmount CHF 1\'083.03\nTotal {cac|P|SL} {ta|SL|N}\n[END]\ntransType=DIVIDEND|Dividend\ndateFormat=dd.MM.yyyy', '2016-05-16', 'en', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:15:17', 3),
(26, 3, 0, 'Rückkaufangebot Yellowtrade', 9, 'GEMAESS DEN ERHALTENEN INSTRUKTIONEN NEHMEN WIR FOLGENDE BUCHUNG VOR :\nCHF {units|PL} 3.15 % NOTES CHF 2012 - 16.12.2016(1) VALOREN-NR 19,372,428\nLOAN PART ISIN {isin|P|N}\nVTB CAPITAL SA\nTITEL, DIE WIR IHREM DEPOT ENTNEHMEN IM DEPOT BEI : SIX SIS AG.\n{transType|PL|P} E.O./51068/AIM\nBRUTTOBETRAG CHF 30 588,00\nEIDG. STEMPEL AUSLAND CHF {tt1|SL|N}\nNETTOBETRAG CHF 30 542,10\nWELCHEN BETRAG WIR DEM KONTO GUTSCHREIBEN CHF R 5116.67.01 GRAF HUGO {cac|SL} {ta|SL|N}\nVALUTA : {datetime|P|N}\n[END]\ntransType=REDUCE|RUECKKAUFANGEBOT\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2000-01-01', 'de', 1, '2021-02-08 17:22:16', 1, '2021-04-29 18:51:27', 7),
(28, 4, 0, 'Kauf und Verkauf Wertpapier', 0, 'Währung: {cac|P|N}\nKonto/Konten: 28500/702484CHF\nStock\nInstrument iShares J.P.Morgan $ EM Bond CHF Symbol {symbol|P|N}\nHedged UCITS ETF\nK/V {transType|P|SL} Open/Close ToOpen\nExchange Description SIX Swiss Exchange (ETFs) Börsenname oder OTC Exchange\nAnzahl {units|P|N} Preis {quotation|P|N}\nValutadatum 05-Dec-2016 Trade Value -18,640\nTradeTime {datetime|P|N} Trade-ID 1050435068\nOrder-ID 927439222 Spread Costs 0.00\nOrdertyp Not Available Total trading costs -50\nBuchungsbetrag-ID Handelsdatum Valutadatum Anzahl Umrechnungskurs Currency conversion cost Gebuchter Betrag\nCommission 4523005266 01-Dec-2016 05-Dec-2016 -22.37 1.0000 0.00 {tc1|SL|N}\nShare Amount 4523558817 01-Dec-2016 05-Dec-2016 -18,640.00 1.0000 0.00 -18,640.00\nSwiss Stamp Duty 4524956687 01-Dec-2016 05-Dec-2016 -27.96 1.0000 0.00 {tt1|SL|N}\nForeign\nAll bookings\nNettobetrag 0.00 {ta|SL|N}\nTrade Details Kunde:CT702484 2\nErstellt um:01-May-2018 5:59:26 AM (UTC) Konto/Konten:28500/702484CHF\n[END]\ntransType=ACCUMULATE|Bought\ntransType=REDUCE|Sold\ndateFormat=dd-MMM-yyyy hh:mm:ss a', '2016-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-02-28 15:41:30', 2),
(29, 1, 1, 'Transaction export (english)', 20, 'datetime=Date\norder=Order #\ntransType=Transaction\nsymbol=Symbol\nsn=Name\nisin=ISIN\nunits=Quantity\nquotation=Unit price\ntt1=Costs\nac=Accrued interest\nta=Net Amount\ncac=Currency\n[END]\ntemplateId=1\ntransType=WITHDRAWAL|Paying out\ntransType=WITHDRAWAL|Fx debit comp.\ntransType=WITHDRAWAL|Forex debit\ntransType=DEPOSIT|Payment\ntransType=DEPOSIT|Forex credit\ntransType=DEPOSIT|Fx credit comp.\ntransType=INTEREST_CASHACCOUNT|Interests\ntransType=FEE|Custody Fees\ntransType=ACCUMULATE|Buy\ntransType=REDUCE|Sell\ntransType=DIVIDEND|Dividend\ndateFormat=dd.MM.yyyy HH:mm\ndelimiterField=;\nbond=quotation|%\noverRuleSeparators=All<’\'|.>\n', '2010-01-01', 'en', 1, '2021-02-08 17:22:16', 1, '2021-04-29 18:42:26', 19),
(30, 3, 0, 'Zins für Anleihen mit Quellensteuer', 12, 'Bern, 16.01.2018\n{transType|PL|P}\nUnsere Referenz: 139280138 \nIm Hinblick auf folgenden Titel:\nTitel\nBezeichnung Anzahl\nISIN: {isin|P|N}\n4 ALFA 13-18 20\'000\nAusführungsdatum {datetime|P|N}\nValutadatum\nNominal {units|P|N}\nFür die Zahlung verwendeter Zinssatz {quotation|P|SL} {per|N|SL}\nBetrag {cin|P} 41.40\nQuellensteuer 15% (ZA) ZAR {tt1|SL|N|O}\nWechselkurs {cex|SL|N|O}\nTotal {cac|P|SL} {ta|SL|N}\n[END]\ntransType=DIVIDEND|Zins\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:26:25', 5),
(31, 3, 0, 'Option Premium', 14, 'Bern, 16.01.2018\n{transType|PL|P|N} Premium\nUnsere Referenz: 139280138 \nIm Hinblick auf folgenden Titel:\nTitel\nBezeichnung Anzahl\nISIN: {isin|P|N}\nHOCHDORF CV 3.5% 30.03.2020 15\'000\nAusführungsdatum {datetime|P|N}\nValutadatum\nAnzahl {units|P|N}\nPrämienanteil {quotation|P|SL} {per|N|SL}\nBetrag {cin|P} 41.40\nWechselkurs {cex|P|N|O}\nTotal {cac|P|SL} {ta|SL|N}\n[END]\ntransType=DIVIDEND|Option\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>', '2016-05-16', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:26:47', 2),
(32, 1, 0, 'Kapitalgewinn ', 12, 'Gland, {datetime|P|N}\n{transType|P|N} Unsere Referenz: 66622789 \nIm Hinblick auf folgenden Titel:\nTitel\nISHARES $ CORP BND ISIN: {isin|P} 490\nNKN: 1613957\nWir haben Ihrem Konto den folgenden Betrag gutgeschrieben: \nKontonummer 000000\nAusführungsdatum 21.05.2014\nValutadatum 11.06.2014\nAnzahl {units|P|N}\nKapitalgewinn {quotation|P} CHF\nBetrag {cac|SL|PL} 416.11\nTotal CHF {ta|SL|N}\n[END]\r\ntemplatePurpose=Kapitalgewinn \r\ntransType=DIVIDEND|Kapitalgewinn\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n\n\n', '2011-10-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-04-30 13:47:48', 16),
(33, 5, 0, 'Kauf und Verkauf', 0, 'Belegnummer\n{transType|P|NL} {units|PL|NL} ISHARES ATX UCITS ETF ({isin|Pc|Nc|N}) um {quotation|NL|P} {cin|NL|N}\nInstrument: Trackers/ETF\nAuftragserstellung : 14/05/2020 09:56:14 CET\nAuftragstyp : Limit (22,50 EUR)\nGültigkeit : Day\nAusführungsdatum und -zeit : {datetime|SL|PL} 10:01:11 CET \nAusführungsort : XETRA\nBruttobetrag 13.500,00 EUR\nTransaktionskosten {tc1|P|SL} EUR\nStempelsteuer (Schweiz) {tt1|SL|PL|O} EUR\n{ta|PL|P} {cac|PL|N}\nLastschrift 13.555,25 EUR Valutadatum 18/05/2020\n[END]\ntransType=ACCUMULATE|Kauf\ntransType=REDUCE|Verkauf\ndateFormat=dd/MM/yyyy\noverRuleSeparators=All<.|,>\n\n', '2015-01-01', 'de', 1, '2021-02-23 09:27:22', 1, '2021-03-19 07:28:57', 13),
(34, 5, 0, 'Dividende / Gutschrift', 13, 'Typ: Foreign stocks\n{transType|P|N} Record date 17/12/2020\n{units|P|NL} Vanguard FTSE Japan ETF {quotation|NL} USD\nBruttobetrag 157,88 USD (139,71 CHF)\nVerwendete Wechselkurse 1 USD = 0,8849 CHF\nDiese Mitteilung dient ausschliesslich zu Informationszwecken. Sie ist und bleibt kein offizieller Beleg\nfür die Zuteilung oder Rückerstattung der erhobenen Steuer.\nNettoguthaben {ta|SL|P} {cac|SL|N} Datum  {datetime|SL|N}\nWertschriftenkonto:10/146992 Wertpapier:{isin|Pc|N} Belegnr.: CPN / 232207\n[END]\ntransType=DIVIDEND|GUTSCHRIFT\ndateFormat=dd/MM/yyyy\noverRuleSeparators=All<.|,>\n\n', '2015-01-06', 'de', 1, '2021-02-23 09:27:22', 1, '2021-05-01 07:29:14', 8),
(36, 6, 1, 'Kauf und Verkauf Aktien', 0, 'date=Datum\ntime=Uhrzeit\nsn=Produkt\nisin=ISIN\nunits=Anzahl\nquotation=Kurs\ncin=cin\ncex=Wechselkurs\ntc1=Gebühr\ncct=cct\nta=Gesamt\ncac=cac\norder=Order-ID\n[END]\ntemplateId=1\ntransType=ACCUMULATE|Buy\ntransType=REDUCE|Sell\ndateFormat=dd.MM.yyyy\ntimeFormat=H:mm\ndelimiterField=;\n', '2015-01-01', 'de', 1, '2021-02-23 08:51:32', 1, '2021-03-02 07:04:31', 10),
(37, 1, 1, 'Transaktions Export', 20, 'datetime=Date\norder=Order #\ntransType=Transaction\nsymbol=Symbol\nsn=Name\nisin=ISIN\nunits=Quantity\nquotation=Unit price\ntt1=Costs\nac=Accrued interest\nta=Net Amount\ncac=Currency\n[END]\ntemplateId=2\ntransType=WITHDRAWAL|Auszahlung\ntransType=WITHDRAWAL|Forex-Belastung\ntransType=WITHDRAWAL|Fx-Belastung Comp.\ntransType=DEPOSIT|Vergütung\ntransType=DEPOSIT|Forex-Gutschrift\ntransType=DEPOSIT|Fx-Gutschrift Comp.\ntransType=INTEREST_CASHACCOUNT|Zins\ntransType=FEE|Depotgebühren\ntransType=ACCUMULATE|Kauf\ntransType=REDUCE|Verkauf\ntransType=DIVIDEND|Dividende\ndateFormat=dd.MM.yyyy HH:mm\ndelimiterField=;\nbond=quotation|%\noverRuleSeparators=All<’\'|.>', '2010-01-01', 'de', 1, '2021-03-02 15:53:55', 1, '2021-04-29 18:43:49', 2),
(38, 1, 0, 'Dividende Zeilenumbruch', 13, 'Gland, {datetime|P|N}\n{transType|P|N} Unsere Referenz: 78811438 \nIm Hinblick auf folgenden Titel:\nTitel\nOriginal Original Anzahl\nISIN: {isin|P|N}\nISHARES $ CORP BND 490\nNKN: 1613957\nWir haben Ihrem Konto den folgenden Betrag gutgeschrieben: \nKontonummer 46595501\nAusführungsdatum 26.02.2015\nValutadatum 19.03.2015\nAnzahl\n{units|PL|P|N}\nDividende\n{quotation|PL|P} USD\nBetrag\nUSD\n430.17\n{cac|P|N|NL}\nTotal  {ta|SL|N}\n[END]\r\ntemplatePurpose=Dividende Zeilenumbruch\ntransType=DIVIDEND|Dividende\ndateFormat=dd.MM.yyyy\noverRuleSeparators=All<’\'|.>\n', '2011-01-01', 'de', 1, '2021-05-01 09:20:41', 1, '2021-05-01 09:20:41', 0);


ALTER TABLE `imp_trans_template` DROP INDEX IF EXISTS `UNIQUE_imptrastemplate_field`;