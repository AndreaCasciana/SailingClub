package guiController;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import system.CreditCard;
import system.Payment;
import system.Staff;
import system.TransferReciept;

/**
 * 
 * Payment GUI controller
 *
 */
public class paymentController {
	
	@FXML 
	private JFXComboBox<String> paymentMethod;
	@FXML
	private VBox cardsView;
	@FXML
	private Text amountToPay;
	@FXML
	private AnchorPane cardOption;
	@FXML
	private AnchorPane addCardView;
	@FXML
	private ImageView cardTypeIMG;
	@FXML
	private JFXButton addCard;
	@FXML
    private TextField cardName;
    @FXML
    private TextField cardNumber;
    @FXML
    private TextField ccv;
    @FXML
    private TextField expMonth;
    @FXML
    private TextField expYear;
    @FXML
    private Text dateErrorMsg;
    @FXML
    private Text cardNumberErrorMsg;
	@FXML
	private JFXButton completePaymentBtn;
	@FXML
	private AnchorPane transferOption;
	@FXML
	private TextField cause;
	@FXML
	private TextField amount;
	@FXML
	private TextField benefactor;
	@FXML
	private TextField bank;
	@FXML
	private TextField iban;
	@FXML
	private TextField payerAccountHolder;
	@FXML
	private TextField payerBank;
	@FXML
	private TextField payerIBAN;
	@FXML
	private JFXButton saveTransferRecieptBtn;
    @FXML
    private Text payerIbanError;
	
	private Map<String, String> imagesPath;
	private String regex;
	private Pattern pattern;
	private String cardType;
	
	/**
	 * payment to be made
	 */
	public static Payment payment;
	/**
	 * keeps track if payment was concluded successfully
	 */
	public static boolean paidSuccessfully;
	
	/**
	 * Initialise the payment window
	 */
	@FXML
	void initialize() {
		
		paidSuccessfully = false;
		
		imagesPath = new HashMap<String, String>();
		imagesPath.put("visa", "/utility/images/visa.png");
		imagesPath.put("mastercard", "/utility/images/mastercard.png");
		imagesPath.put("discover", "/utility/images/discover.png");
		imagesPath.put("amex", "/utility/images/amex.png");
		imagesPath.put("jcb", "/utility/images/jcb.png");
		imagesPath.put("diners", "/utility/images/diners.png");
		
		cardOption.setVisible(false);
		addCardView.setVisible(false);
		completePaymentBtn.setDisable(true);
		cardNumberErrorMsg.setVisible(false);
		transferOption.setVisible(false);
		saveTransferRecieptBtn.setDisable(true);
		payerIbanError.setVisible(false);
		
		paymentMethod.getItems().addAll(
			    "Credit Card",
			    "Bank Transfer"
		);
		
		addCards();
		
		regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
			    "(?<mastercard>5[1-5][0-9]{14})|" +
			    "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
			    "(?<amex>3[47][0-9]{13})|" +
			    "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
			    "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";
		pattern = Pattern.compile(regex);
		addListener();
	}
	
	/**
	 * sets the payment method as 'credit card' or 'transfer' based on the user's preference
	 * @param event the ActionEvent
	 */
	@FXML
	void setPaymentMethod(ActionEvent event) {
		cardOption.setVisible(false);
		addCardView.setVisible(false);
		transferOption.setVisible(false);
		
		if(paymentMethod.getValue().equals("Credit Card")) {
			amountToPay.setText(payment.getCause() + ": $"+payment.getAmount());
			cardOption.setVisible(true);
		}
		else if(paymentMethod.getValue().equals("Bank Transfer")) {
			Map<String, String> benefactorDetails = new Staff().getBenefactorDetails();
			benefactor.setText(benefactorDetails.get("benefactor"));
			bank.setText(benefactorDetails.get("bank"));
			iban.setText(benefactorDetails.get("IBAN"));
			amount.setText("$"+payment.getAmount());
			cause.setText(payment.getCause().toString());
			saveTransferRecieptBtn.setText("Complete payment ($" + payment.getAmount() + ")");
			transferOption.setVisible(true);
		}
	}
	
	/**
	 * displays the view to permit adding new credit card details for the payment
	 * @param event the ActionEvent
	 */
	@FXML
	void addCard(ActionEvent event) {
		cardOption.setVisible(false);
		completePaymentBtn.setText("Complete payment ($" + payment.getAmount() + ")");
		addCardView.setVisible(true);
	}

	/**
	 * closes the view for adding new credit card and goes back to the previous page
	 * @param event the ActionEvent
	 */
	@FXML
    void cancelAddCard(ActionEvent event) {
		  addCardView.setVisible(false);
		  cardOption.setVisible(true);
    }

	/**
	 * shift the focus to the next text field (card number)
	 * @param event the KeyEvent
	 */
	@FXML
    void requestCardNumberFocus(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER )
			cardNumber.requestFocus();
    }

	/**
	 * shift the focus to the next text field (expiration month)
	 * @param event the KeyEvent
	 */
	@FXML
    void requestExpMonthFocus(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER )
			expMonth.requestFocus();
    }

	/**
	 * shift the focus to the next text field (expiration year)
	 * @param event the KeyEvent
	 */
    @FXML
    void requestExpYearFocus(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER )
			expYear.requestFocus();
    }
    
	/**
	 * shift the focus to the next text field (cvv)
	 * @param event the KeyEvent
	 */
    @FXML
    void requestCCVFocus(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER )
			ccv.requestFocus();
    }
    
	/**
	 * proceeds with the payment after new card is added
	 * returns an error message if the details provided are not valid
	 * @param event the ActionEvent
	 */
    @FXML
    void completePayment(ActionEvent event) {
    	if(!cardNumberIsValid(cardNumber.getText())) {
    		cardNumberErrorMsg.setVisible(true);
    		return;
    	}
    	
    	if(!expMonth.getText().matches("0?[1-9]|1[012]") ){
			dateErrorMsg.setText("error in exp. date");
			return;
		}
		
		String year = expYear.getText();
		String month = expMonth.getText();
		if(year.length() == 2)
			year = "20" + year;
		if(month.length() == 1)
			month = "0" + month;
		
		
		if(Integer.parseInt(year) < LocalDate.now().getYear()) {
			dateErrorMsg.setText("card expired.");
			return;
		}else if(Integer.parseInt(year) == LocalDate.now().getYear()) {
			if(Integer.parseInt(month) < LocalDate.now().getMonthValue()) {
    			dateErrorMsg.setText("card expired.");
    			return;
    		}
		}
    	
    	if(!AlertDialogue.confirm("Comfirm payment of $" + payment.getAmount(), "Confirm", "Cancel"))
    		AlertDialogue.warning("Payment Transaction Cancelled.");
    	else {
    		int idCard = new CreditCard().addCard(new CreditCard(cardType, cardName.getText().trim(), new BigInteger(cardNumber.getText()), Integer.parseInt(expMonth.getText()), Integer.parseInt(expYear.getText())));
    		
    		if(idCard == 0) {
    			AlertDialogue.error("Error occured. (DB error)");
    			return;
    		}
    		
    		payment.setMethod("card");
    		payment.setMethodID_CC(idCard);
    		payment.setDate(Date.valueOf(LocalDate.now()));
    		if(payment.addPayment(payment)) {
    			AlertDialogue.info("Transaction Concluded Successfully.");
    			Node node = (Node) event.getSource();
    		    Stage parent = (Stage) node.getScene().getWindow();
    		    paidSuccessfully = true;
        		parent.close();
    		}
    		else
    			AlertDialogue.error("Error occured during payment. (DB)");
    	}
    }
    
	/**
	 * and proceeds with the transfer payment
	 * @param event the ActionEvent
	 */
    @FXML
    void saveTransferReciept(ActionEvent event) {
    	if(!isIbanValid(payerIBAN.getText())) {
    		payerIbanError.setVisible(true);
    		return;
    	}
    	
    	if(!AlertDialogue.confirm("Comfirm paid fee of $" + payment.getAmount(), "Confirm", "Cancel"))
    		AlertDialogue.warning(" Cancelled.");
    	else {
    		int idTransfer = new TransferReciept().addTransfer(new TransferReciept(payerIBAN.getText(), payerBank.getText(), payerAccountHolder.getText()));
    		if(idTransfer == 0) {
    			AlertDialogue.error("Error occured. (DB error)");
    			return;
    		}
    		
    		payment.setMethod("transfer");
    		payment.setMethodID_TR(idTransfer);
    		payment.setDate(Date.valueOf(LocalDate.now()));
    		if(payment.addPayment(payment)) {
    			AlertDialogue.info("Transaction Concluded Successfully.");
    			Node node = (Node) event.getSource();
    		    Stage parent = (Stage) node.getScene().getWindow();
    		    paidSuccessfully = true;
        		parent.close();
    		}
    		else
    			AlertDialogue.error("Error occured during payment.");
    	}
    }
    
	/**
	 * shift the focus to the next text field (payer bank)
	 * @param event the KeyEvent
	 */
    @FXML
    void requestPayerBankFocus(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER )
			payerBank.requestFocus();
    }

	/**
	 * shift the focus to the next text field (payer IBAN)
	 * @param event the KeyEvent
	 */
    @FXML
    void requestPayerIBANFocus(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER )
			payerIBAN.requestFocus();
    }
	
    /**
     * add member cards from database to cards view
     */
	private void addCards() {
		ArrayList<CreditCard> cards = new CreditCard().getCards(MemberController.member.getId());
		for(CreditCard card : cards) {
			ImageView cardIcon = new ImageView();
			VBox cardInfo = new VBox();
			if(card.getType().toLowerCase().equals("visa")) 
				cardIcon = new ImageView(new Image(getClass().getResource(imagesPath.get("visa")).toExternalForm()));
			else if(card.getType().toLowerCase().equals("mastercard")) 
				cardIcon = new ImageView(new Image(getClass().getResource(imagesPath.get("mastercard")).toExternalForm()));
			else if(card.getType().toLowerCase().equals("discover")) 
				cardIcon = new ImageView(new Image(getClass().getResource(imagesPath.get("discover")).toExternalForm()));
			else if(card.getType().toLowerCase().equals("amex")) 
				cardIcon = new ImageView(new Image(getClass().getResource(imagesPath.get("amex")).toExternalForm()));
			else if(card.getType().toLowerCase().equals("jcb")) 
				cardIcon = new ImageView(new Image(getClass().getResource(imagesPath.get("jcb")).toExternalForm()));
			else if(card.getType().toLowerCase().equals("diners")) 
				cardIcon = new ImageView(new Image(getClass().getResource(imagesPath.get("diners")).toExternalForm()));
			cardIcon.setFitWidth(100);
			cardIcon.setFitHeight(100);
			cardInfo.getChildren().add(new Text(card.getType()+" ••••"+card.getCardNumber().toString().substring (card.getCardNumber().toString().length()-4, card.getCardNumber().toString().length())));
			cardInfo.getChildren().add(new Text(card.getExpMonth()+"/"+card.getExpYear()));
			
			JFXButton remove = new JFXButton("remove card");
			JFXButton payWithCard = new JFXButton("pay with this card");
			remove.setStyle("-fx-background-color: red; ");
			payWithCard.setStyle("-fx-background-color: lightgreen; ");
			
			VBox vbox = new VBox(30,cardInfo, new HBox(10, payWithCard, remove));
			vbox.setAlignment(Pos.BASELINE_CENTER);

			cardsView.getChildren().add(new HBox(5,cardIcon, vbox));
			
			remove.setOnAction(e ->{
				if(AlertDialogue.confirm("Confirm Card removal?", "Remove", "Cancel"))
					if(card.removeCard(card.getId())) {
						AlertDialogue.warning("Card removed from payments options.");
						cardsView.getChildren().clear();
						addCards();
					}else
						AlertDialogue.error("Error occured.");
			});
			
			int year = card.getExpYear();
			int month = card.getExpMonth();
			if(year < LocalDate.now().getYear()) {
				payWithCard.setText("card is expired");
				payWithCard.setDisable(true);
			}else if(year == LocalDate.now().getYear()) {
				if(month < LocalDate.now().getMonthValue()) {
					payWithCard.setText("card is expired");
					payWithCard.setDisable(true);
	    		}
			}
			
			payWithCard.setOnAction(e ->{
				final Stage dialog = new Stage();
				
				ImageView cardIcon1 = new ImageView();
				VBox cardInfo1 = new VBox();
				if(card.getType().toLowerCase().equals("visa")) 
					cardIcon1 = new ImageView(new Image(getClass().getResource(imagesPath.get("visa")).toExternalForm()));
				else if(card.getType().toLowerCase().equals("mastercard")) 
					cardIcon1 = new ImageView(new Image(getClass().getResource(imagesPath.get("mastercard")).toExternalForm()));
				cardIcon1.setFitWidth(80);
				cardIcon1.setFitHeight(80);
				cardInfo1.getChildren().add(new Text(card.getType()+" ••••"+card.getCardNumber().toString().substring (card.getCardNumber().toString().length()-4, card.getCardNumber().toString().length())));
				cardInfo1.getChildren().add(new Text(card.getExpMonth()+"/"+card.getExpYear()));
				cardInfo1.setAlignment(Pos.CENTER);
				TextField ccv = new TextField();
				ccv.setPromptText("Enter CCV");
				JFXButton pay = new JFXButton("pay");
				pay.setStyle("-fx-background-color: lightgreen; ");
				HBox ccvBox = new HBox(5,new Text("CCV: "), ccv, pay);
				ccvBox.setAlignment(Pos.CENTER);
				VBox vbox1 = new VBox(5, cardIcon1, cardInfo1, ccvBox);
				vbox1.setAlignment(Pos.CENTER);
                
                pay.setDisable(true);
                ccv.textProperty().addListener((observable, oldValue, newValue) -> {
                	if (!newValue.matches("\\d*")) 
                        ccv.setText(newValue.replaceAll("[^\\d]", ""));
                	if(ccv.getText().length() > 4)
                		ccv.setText(oldValue);
                	pay.setDisable(ccv.getText().length() != 3 && ccv.getText().length() != 4);
                });
                ccv.setOnKeyReleased(event -> {
                	  if (event.getCode() == KeyCode.ENTER){
                		    pay.fire();
                		  }
                		});
                
                pay.setOnAction(e1 -> {
                	dialog.close();
                	if(!AlertDialogue.confirm("Comfirm payment of $" + payment.getAmount(), "Confirm", "Cancel"))
                		AlertDialogue.warning("Payment Transaction Cancelled.");
                	else {
                		payment.setMethod("card");
                		payment.setMethodID_CC(card.getId());
                		payment.setDate(Date.valueOf(LocalDate.now()));
                		if(payment.addPayment(payment)) {
                			AlertDialogue.info("Transaction Concluded Successfully.");
                    		Stage parent = (Stage)payWithCard.getScene().getWindow();
                    		paidSuccessfully = true;
                    		parent.close();
                		}
                		else
                			AlertDialogue.error("Error occured during payment.");
                	}
                });
                
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.setResizable(false);
                dialog.initOwner(cardsView.getScene().getWindow());
                dialog.setScene(new Scene(vbox1, 300, 200));
                dialog.getIcons().add(new Image(getClass().getResource("/utility/images/logo.png").toExternalForm(), 15, 15, true, true));
                dialog.showAndWait();
                
			});
		}
	}
	
	/**
	 * adds an event listener to the text fields for avoiding the insertion of incorrect values
	 */
	private void addListener() {
		ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
			StringProperty textProperty = (StringProperty) observable ;
			TextField textField = (TextField) textProperty.getBean();
			if(newValue.trim().length() > 0) {
				if(textField == cardName || textField == payerAccountHolder || textField == payerBank) {
					newValue = newValue.stripLeading();  
					if (!newValue.matches("\\sa-zA-Z*")) 
						newValue = newValue.replaceAll("[^\\sa-zA-Z]", "");
					textField.setText(newValue.toUpperCase().replaceAll("\\s+", " "));
				}
				if(textField == cardNumber || textField == expMonth || textField == expYear || textField == ccv) {
					if (!newValue.matches("\\d*")) 
						textField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if(textField == cardNumber) {
					if(newValue.length() > 16)
						textField.setText(oldValue);
					else {
						if(cardNumberIsValid(textField.getText())) {
							Matcher matcher = pattern.matcher(textField.getText());
							if(matcher.matches()) {
								if(matcher.group("visa") != null)
									cardType = "visa";									
								else if(matcher.group("mastercard") != null)
									cardType = "mastercard";
								else if(matcher.group("discover") != null)
									cardType = "discover";
								else if(matcher.group("amex") != null)
									cardType = "amex";
								else if(matcher.group("jcb") != null)
									cardType = "jcb";
								else if(matcher.group("diners") != null)
									cardType = "diners";
								
								if(!cardType.equals(""))
									cardTypeIMG.setImage(new Image(getClass().getResource(imagesPath.get(cardType)).toExternalForm()));
								else 
									cardTypeIMG.setImage(null);	
								
							}else
								cardTypeIMG.setImage(null);
						}else
							cardTypeIMG.setImage(null);
					}
				}
				if(textField == expMonth) {
					if(newValue.length() > 2)
						textField.setText(oldValue);
				}
				if(textField == expYear || textField == ccv) {
					if(newValue.length() > 4)
						textField.setText(oldValue);
				}
				if(textField == payerIBAN) {
					if(newValue.length() > 34)
						textField.setText(oldValue);
					else {
						newValue = newValue.trim();  
						if (!newValue.matches("\\a-zA-Z0-9*")) 
							textField.setText(newValue.replaceAll("[^\\a-zA-Z0-9*]", "").toUpperCase());
					}
				}
			}else
				textField.setText(newValue.stripLeading());
			completePaymentBtn.setDisable((cardNumber.getText().trim().length() < 13) || (cardName.getText().trim().length() == 0)
											|| ((expMonth.getText().trim().length() != 1) && (expMonth.getText().trim().length() != 2))
											|| ((expYear.getText().trim().length() != 2) && (expYear.getText().trim().length() != 4))
											|| ((ccv.getText().trim().length() != 3) && (ccv.getText().trim().length() != 4)));
			saveTransferRecieptBtn.setDisable(payerAccountHolder.getText().trim().length() == 0 || payerBank.getText().trim().length() == 0 
												|| payerIBAN.getText().trim().length() < 15 || payerIBAN.getText().trim().length() > 34);
			dateErrorMsg.setText("");
			cardNumberErrorMsg.setVisible(false);
			payerIbanError.setVisible(false);
		});
		cardName.textProperty().addListener(listener);
		cardNumber.textProperty().addListener(listener);
		expMonth.textProperty().addListener(listener);
		expYear.textProperty().addListener(listener);
		ccv.textProperty().addListener(listener);
		payerAccountHolder.textProperty().addListener(listener);
		payerBank.textProperty().addListener(listener);
		payerIBAN.textProperty().addListener(listener);
	}
	
	/**
	 * verifies if credit card number is valid
	 * @param ccNumber to be verified
	 * @return true if valid
	 */
	private static boolean cardNumberIsValid(String ccNumber) // checksum
	  {
	    int sum = 0;
	    boolean alternate = false;
	    for (int i = ccNumber.length() - 1; i >= 0; i--)
	    {
	      int n = Integer.parseInt(ccNumber.substring(i, i + 1));
	      if (alternate)
	      {
	        n *= 2;
	        if (n > 9)
	        {
	          n = (n % 10) + 1;
	        }
	      }
	      sum += n;
	      alternate = !alternate;
	    }
	    return (sum % 10 == 0);
	  }
	
	/**
	 * verifies if IBAN is valid
	 * @param iban to be verified
	 * @return true if valid
	 */
	private boolean isIbanValid(String iban) {

	    int IBAN_MIN_SIZE = 15;
	    int IBAN_MAX_SIZE = 34;
	    long IBAN_MAX = 999999999;
	    long IBAN_MODULUS = 97;

	    String trimmed = iban.trim();

	    if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
	        return false;
	    }

	    String reformat = trimmed.substring(4) + trimmed.substring(0, 4);
	    long total = 0;

	    for (int i = 0; i < reformat.length(); i++) {

	        int charValue = Character.getNumericValue(reformat.charAt(i));

	        if (charValue < 0 || charValue > 35) {
	            return false;
	        }

	        total = (charValue > 9 ? total * 100 : total * 10) + charValue;

	        if (total > IBAN_MAX) {
	            total = (total % IBAN_MODULUS);
	        }
	    }

	    return (total % IBAN_MODULUS) == 1;
	}


}
