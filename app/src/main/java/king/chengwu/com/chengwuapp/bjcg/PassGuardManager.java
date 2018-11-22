package king.chengwu.com.chengwuapp.bjcg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import cn.passguard.PassGuardEdit;
import cn.passguard.doAction;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class PassGuardManager {
	private static Context m_context;
	private WebView webView;
	private static PassGuardManager INSTANCE = new PassGuardManager();

	HashMap<String, PassGuardEdit> manager = new HashMap<String, PassGuardEdit>();

	/**
	 * singleton
	 */
	private PassGuardManager() {

	}

	/**
	 * Get singleton instance.
	 */
	public static PassGuardManager getInstance(Context context) {

		m_context = context;
		return INSTANCE;
	}

	public void setWebView(WebView wv) {
		webView = wv;
	}

	@JavascriptInterface
	public boolean hasPassGuard(String id) {
		return manager.containsKey(id);
	}

	@JavascriptInterface
	public void newPassGuard(final String id) {
		final PassGuardEdit passGuardEdit = new PassGuardEdit(m_context, null);
		passGuardEdit.m_strid = id;
		passGuardEdit.m_webview = webView;
		doAction hideaction = new doAction() {
			@Override
			public void doActionFunction() {
				((Activity) m_context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						webView.loadUrl("javascript:"
								+ "var keyboard = document.getElementById('keyboard');"
								+ "document.body.removeChild(keyboard);");
						webView.loadUrl("javascript:doHideAction(\"" + id
								+ "\")");
					}
				});
			}
		};
		doAction showaction = new doAction() {
			@Override
			public void doActionFunction() {
				((Activity) m_context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						webView.loadUrl("javascript:"
								+ "var edittext = document.getElementById('"
								+ id
								+ "');"
								+ "var keyboardheight = "
								+ passGuardEdit.getHeight()
								/ webView.getScale()
								+ ";"
								+ "var clientHeight;"
								+ "if ( document.compatMode == 'CSS1Compat' ) {"
								+ "clientHeight = document.documentElement.clientHeight;"
								+ "} else {"
								+ "clientHeight = document.body.clientHeight;"
								+ "}"
								+ "var actualTop = edittext.offsetTop; var current = edittext.offsetParent; while (current !== null){actualTop += current.offsetTop; current = current.offsetParent;};"
								+ "var screenbottom = clientHeight - actualTop + document.body.scrollTop - edittext.clientHeight;"
								+ "var keyboard = document.createElement('div');"
								+ "keyboard.style.height = keyboardheight + 'px';"
								+ "keyboard.id = 'keyboard';"
								+ "document.body.appendChild(keyboard);"
								+ "if (screenbottom < keyboardheight) {"
								+ "document.body.scrollTop = actualTop + edittext.clientHeight - clientHeight + keyboardheight;"
								+ "}");
						webView.loadUrl("javascript:doShowAction(\"" + id
								+ "\")");
					}
				});
			}
		};
		passGuardEdit.setKeyBoardShowAction(showaction);
		passGuardEdit.setKeyBoardHideAction(hideaction);
		passGuardEdit.EditTextAlwaysShow(true);
		manager.put(id, passGuardEdit);
	}

	@JavascriptInterface
	public void initPassGuardKeyBoard(String id) {
		manager.get(id).initPassGuardKeyBoard();
	}

	@JavascriptInterface
	public void StartPassGuardKeyBoard(String id) {
		StopPassGuardKeyBoardAllExceptID(id);
		manager.get(id).StartPassGuardKeyBoard();
	}

	@JavascriptInterface
	public void StopPassGuardKeyBoard(String id) {
		manager.get(id).StopPassGuardKeyBoard();
	}

	@JavascriptInterface
	public void StopPassGuardKeyBoardAll() {
		Iterator<Entry<String, PassGuardEdit>> it = manager.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, PassGuardEdit> entry = it.next();

			if (entry.getValue().isKeyBoardShowing()) {
				entry.getValue().StopPassGuardKeyBoard();
			}
		}
	}

	@JavascriptInterface
	public void StopPassGuardKeyBoardAllExceptID(String id) {
		Iterator<Entry<String, PassGuardEdit>> it = manager.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, PassGuardEdit> entry = it.next();

			if (entry.getKey().equals(id))
				continue;

			if (entry.getValue().isKeyBoardShowing()) {
				entry.getValue().StopPassGuardKeyBoard();
			}
		}
	}

	@JavascriptInterface
	public void setEncrypt(String id, boolean need) {
		manager.get(id).setEncrypt(need);
	}
	
	@JavascriptInterface
	public void setWebViewSyn(String id, boolean need) {
		manager.get(id).setWebViewSyn(need);
	}
	@JavascriptInterface
	public void setButtonPress(String id, boolean need) {
		manager.get(id).setButtonPress(need);
	}

	@JavascriptInterface
	public void setMaxLength(String id, int MaxLength) {
		manager.get(id).setMaxLength(MaxLength);
	}

	@JavascriptInterface
	public void setInputRegex(String id, String regex) {
		manager.get(id).setInputRegex(regex);
	}

	@JavascriptInterface
	public void setMatchRegex(String id, String regex) {
		manager.get(id).setMatchRegex(regex);
	}

	@JavascriptInterface
	public void setReorder(String id, int type) {
		manager.get(id).setReorder(type);
	}

	@JavascriptInterface
	public boolean checkMatch(String id) {
		return manager.get(id).checkMatch();
	}

	@JavascriptInterface
	public int[] getPassLevel(String id) {
		return manager.get(id).getPassLevel();
	}

	@JavascriptInterface
	public void useNumberPad(String id, boolean use) {
		manager.get(id).useNumberPad(use);
	}

	@JavascriptInterface
	public void setCipherKey(String id, String key) {
		manager.get(id).setCipherKey(key);
	}

	@JavascriptInterface
	public String getOutput1(String id) {
		return manager.get(id).getAESCiphertext();
	}

	@JavascriptInterface
	public String getText(String id) {
		return manager.get(id).getText().toString();
	}

	@JavascriptInterface
	public String getOutput2(String id) {
		return manager.get(id).getMD5();
	}

	@JavascriptInterface
	public int getOutput3(String id) {
		return manager.get(id).getLength();
	}

	@JavascriptInterface
	public boolean isKeyBoardShowing(String id) {
		return manager.get(id).isKeyBoardShowing();
	}

	@JavascriptInterface
	public boolean hasKeyBoardShowing() {
		Iterator<Entry<String, PassGuardEdit>> it = manager.entrySet()
				.iterator();
		while (it.hasNext()) {
			Entry<String, PassGuardEdit> entry = it.next();

			if (entry.getValue().isKeyBoardShowing()) {
				return true;
			}
		}
		return false;
	}

	@JavascriptInterface
	public void setWatchOutside(String id, boolean need) {
		manager.get(id).setWatchOutside(need);
	}

	@JavascriptInterface
	public void EditTextAlwaysShow(String id, boolean need) {
		manager.get(id).EditTextAlwaysShow(need);
	}

	@JavascriptInterface
	public void clear(String id) {
		manager.get(id).clear();
	}

	@JavascriptInterface
	public void destory(String id) {
		//manager.get(id).destory();
		manager.remove(id);
	}
}
