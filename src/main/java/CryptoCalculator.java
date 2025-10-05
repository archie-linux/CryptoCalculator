import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;

public class CryptoCalculator {
    public static String[] currencies = new String[] {
    		"Fiat Currencies",
    		"",
    		"United States Dollar ($) USD", "Albanian Lek (L) ALL", "Algerian Dinar (د.ج) DZD", "Argentine Peso ($) ARS", "Armenian Dram (֏) AMD", "Australian Dollar ($) AUD", "Azerbaijani Manat (₼) AZN", "Bahraini Dinar (.د.ب) BHD", "Bangladeshi Taka (৳) BDT", "Belarusian Ruble (Br) BYN", "Bermudan Dollar ($) BMD", "Bolivian Boliviano (Bs.) BOB", "Bosnia-Herzegovina Convertible Mark (KM) BAM", "Brazilian Real (R$) BRL", "Bulgarian Lev (лв) BGN", "Cambodian Riel (៛) KHR", "Canadian Dollar ($) CAD", "Chilean Peso ($) CLP", "Chinese Yuan (¥) CNY", "Colombian Peso ($) COP", "Costa Rican Colón (₡) CRC", "Croatian Kuna (kn) HRK", "Cuban Peso ($) CUP", "Czech Koruna (Kč) CZK", "Danish Krone (kr) DKK", "Dominican Peso ($) DOP", "Egyptian Pound (£) EGP", "Euro (€) EUR", "Georgian Lari (₾) GEL", "Ghanaian Cedi (₵) GHS", "Guatemalan Quetzal (Q) GTQ", "Honduran Lempira (L) HNL", "Hong Kong Dollar ($) HKD", "Hungarian Forint (Ft) HUF", "Icelandic Króna (kr) ISK", "Indian Rupee (₹) INR", "Indonesian Rupiah (Rp) IDR", "Iranian Rial (﷼) IRR", "Iraqi Dinar (ع.د) IQD", "Israeli New Shekel (₪) ILS", "Jamaican Dollar ($) JMD", "Japanese Yen (¥) JPY", "Jordanian Dinar (د.ا) JOD", "Kazakhstani Tenge (₸) KZT", "Kenyan Shilling (Sh) KES", "Kuwaiti Dinar (د.ك) KWD", "Kyrgystani Som (с) KGS", "Lebanese Pound (ل.ل) LBP", "Macedonian Denar (ден) MKD", "Malaysian Ringgit (RM) MYR", "Mauritian Rupee (₨) MUR", "Mexican Peso ($) MXN", "Moldovan Leu (L) MDL", "Mongolian Tugrik (₮) MNT", "Moroccan Dirham (د.م.) MAD", "Myanma Kyat (Ks) MMK", "Namibian Dollar ($) NAD", "Nepalese Rupee (₨) NPR", "New Taiwan Dollar (NT$) TWD", "New Zealand Dollar ($) NZD", "Nicaraguan Córdoba (C$) NIO", "Nigerian Naira (₦) NGN", "Norwegian Krone (kr) NOK", "Omani Rial (ر.ع.) OMR", "Pakistani Rupee (₨) PKR", "Panamanian Balboa (B/.) PAB", "Peruvian Sol (S/.) PEN", "Philippine Peso (₱) PHP", "Polish Złoty (zł) PLN", "Pound Sterling (£) GBP", "Qatari Rial (ر.ق) QAR", "Romanian Leu (lei) RON", "Russian Ruble (₽) RUB", "Saudi Riyal (ر.س) SAR", "Serbian Dinar (дин.) RSD", "Singapore Dollar (S$) SGD", "South African Rand (R) ZAR", "South Korean Won (₩) KRW", "South Sudanese Pound (£) SSP", "Sovereign Bolivar (Bs.) VES", "Sri Lankan Rupee (Rs) LKR", "Swedish Krona ( kr) SEK", "Swiss Franc (Fr) CHF", "Thai Baht (฿) THB", "Trinidad and Tobago Dollar ($) TTD", "Tunisian Dinar (د.ت) TND", "Turkish Lira (₺) TRY", "Ugandan Shilling (Sh) UGX", "Ukrainian Hryvnia (₴) UAH", "United Arab Emirates Dirham (د.إ) AED", "Uruguayan Peso ($) UYU", "Uzbekistan Som (so'm) UZS", "Vietnamese Dong (₫) VND",
    		"",
    		"Precious Metals",
    		"",
    		"Gold Troy Ounce XAU", "Silver Troy Ounce XAG", "Platinum Ounce XPT", "Palladium Ounce XPD", 
    		"",
    		"CryptoCurrencies",
    		"",
    		"Bitcoin BTC", "Ethereum ETH", "Binance Coin BNB", "Cardano ADA", "Ripple XRP", "Solana SOL", "Polkadot DOT", "Dogecoin DOGE", "Litecoin LTC", "Chainlink LINK",
    };
    
    static Dotenv dotenv = Dotenv.configure()
    		.directory("./assets")
    		.filename("env")
    		.load();
	
    public static void main(String[] args) throws IOException, InterruptedException
    {        
        JFrame frame = new JFrame("Crypto Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());  
        frame.setSize(500,500);

        // Create a Panel
        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel);
        
        JTextField amountFieldText = new JTextField("Default Value", 20);
        amountFieldText.setText("1");
        panel.add(amountFieldText, setElementConstraints(0, 0));
         
        JComboBox<String> currencyConvertFromDropdown  = new JComboBox<>(currencies);
        currencyConvertFromDropdown.setPreferredSize(new Dimension(250, 50));
        currencyConvertFromDropdown.setSelectedItem("Bitcoin BTC");
        panel.add(currencyConvertFromDropdown, setElementConstraints(0, 1));

        JComboBox<String> currencyConvertToDropdown  = new JComboBox<>(currencies);
        currencyConvertToDropdown.setPreferredSize(new Dimension(250, 50));
        currencyConvertToDropdown.setSelectedItem("United States Dollar ($) USD");
        panel.add(currencyConvertToDropdown, setElementConstraints(0, 2));

        JButton currencyConvertButton = new JButton("Convert");
        panel.add(currencyConvertButton, setElementConstraints(0, 3));
        
        JLabel priceConversionLabel = new JLabel();
        panel.add(priceConversionLabel, setElementConstraints(0, 4));
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Add ActionListener to the button
        currencyConvertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	String currencyFromSymbol = (String)currencyConvertFromDropdown.getSelectedItem();
            	String[] items = currencyFromSymbol.split(" ");

            	String convertToSymbol = (String)currencyConvertToDropdown.getSelectedItem();
            	String[] items1 = convertToSymbol.split(" ");
            	
            	String symbol = items[items.length - 1];
            	String convert = items1[items1.length - 1];
            	String amount = amountFieldText.getText();
            	
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://pro-api.coinmarketcap.com/v2/tools/price-conversion?symbol=" + symbol + "&convert=" + convert + "&amount=" + amount))
                    .header("Accept", "application/json")
                    .header("X-CMC_PRO_API_KEY", dotenv.get("API_KEY"))
                    .build();

                HttpResponse<String> response;
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
				try {
					response = client.send(request, HttpResponse.BodyHandlers.ofString());
	                ObjectMapper objectMapper = new ObjectMapper();
	                JsonNode jsonNode = objectMapper.readTree(response.body());
	                
	                JsonNode data = jsonNode.get("data");
//	                System.out.println(data);
	                for (JsonNode sym: data) {
	                	if (currencyFromSymbol.contains(sym.get("name").asText())) {
			                Double price = sym.get("quote").get(convert).get("price").asDouble();
			                String roundedPrice = decimalFormat.format(price);
			                String result = amount + " " + currencyFromSymbol + " = " + roundedPrice + " " + convertToSymbol;
			                priceConversionLabel.setText(result);
	                	}
	                }
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
            }
       });
    }
 
 
    public static GridBagConstraints setElementConstraints(int x, int y) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        return constraints;
    }
}