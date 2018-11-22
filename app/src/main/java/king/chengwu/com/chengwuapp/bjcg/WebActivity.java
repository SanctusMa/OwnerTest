package king.chengwu.com.chengwuapp.bjcg;

import android.annotation.SuppressLint;
import android.app.Activity;

@SuppressLint("JavascriptInterface")
public class WebActivity extends Activity {
//	private WebView mWebView = null;
//	private PassGuardManager passGuardManager = null;
//	private String pkg = null;
//	private String cls = null;
//	static {
//		System.loadLibrary("PassGuard");
//	}
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_web);
//		mWebView = (WebView) findViewById(R.id.webview);
//		// 开启JavaScript支持
//		mWebView.getSettings().setJavaScriptEnabled(true);
//		// 允许JavaScript打开窗口
//		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//		// 不允许保存表单数据
//		mWebView.getSettings().setSaveFormData(false);
//		// 不允许保存密码
//		mWebView.getSettings().setSavePassword(false);
//		// 隐藏滚动条（不使用android:scrollbars="none"，因为这样会导致无法在软键盘弹出时自动调整大小）
//		mWebView.setHorizontalScrollBarEnabled(false);// 水平不显示
//		mWebView.setVerticalScrollBarEnabled(false); // 垂直不显示
//
//		mWebView.setWebChromeClient(new WebChromeClient() {
//			@Override
//			public boolean onJsAlert(WebView view, String url, String message,
//					JsResult result) {
//				final AlertDialog.Builder builder = new AlertDialog.Builder(
//						view.getContext());
//				builder.setTitle("提示信息").setMessage(message)
//						.setPositiveButton("确定", null);
//				// 不需要绑定按键事件
//				// 屏蔽keycode等于84之类的按键
//				builder.setOnKeyListener(new OnKeyListener() {
//					@Override
//					public boolean onKey(DialogInterface dialog, int keyCode,
//							KeyEvent event) {
//						return true;
//					}
//				});
//				// 禁止响应按back键的事件
//				builder.setCancelable(false);
//				AlertDialog dialog = builder.create();
//				dialog.show();
//				result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
//				return true;
//			}
//
//			@Override
//			public boolean onJsConfirm(WebView view, String url,
//					String message, final JsResult result) {
//				final AlertDialog.Builder builder = new AlertDialog.Builder(
//						view.getContext());
//				builder.setTitle("确认信息").setMessage(message)
//						.setPositiveButton("确定", new OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								result.confirm();
//							}
//						}).setNeutralButton("取消", new OnClickListener() {
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								result.cancel();
//							}
//						});
//				builder.setOnCancelListener(new OnCancelListener() {
//					@Override
//					public void onCancel(DialogInterface dialog) {
//						result.cancel();
//					}
//				});
//
//				// 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
//				builder.setOnKeyListener(new OnKeyListener() {
//					@Override
//					public boolean onKey(DialogInterface dialog, int keyCode,
//							KeyEvent event) {
//						return true;
//					}
//				});
//				AlertDialog dialog = builder.create();
//				dialog.show();
//				return true;
//			}
//		});
//
//		mWebView.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				if (url.startsWith("http://") || url.startsWith("https://")) {
//					view.loadUrl(url);
//					return true;
//				}
//				return false;
//			}
//
//			@Override
//			public void onPageFinished(WebView view, String url) {
//				super.onPageFinished(view, url);
//			}
//		});
//
//		mWebView.loadUrl("file:///android_asset/login.html");
//		// webView.loadUrl("https://ibank.hrbb.com.cn/loginService.htm");
//		PassGuardEdit
//				.setLicense("Rm84dzZsdmNBU2c4c3lCRmUvUitZV3FVc25qYytubkNNcWw2WGlMeHhFMURVYjdZVXMxdkxDR2haS0dvTmVodGJwTjdROXViRzVTQk1vbThidng0NTFtVks1ZWxlcTZNK3kwdEsvSGVmaDZHcjVBc2VYdUlHQ2JWcEFsTHoyN3Q5VmhDRnIyNVp6SFJCcEF0aTB0TkdaVEFPMkNwUnkwTkFDYjZHdUd3RitBPXsiaWQiOjAsInR5cGUiOiJ0ZXN0IiwicGxhdGZvcm0iOjIsIm5vdGJlZm9yZSI6IjIwMTcwNzAzIiwibm90YWZ0ZXIiOiIyMDE3MTAwMyJ9");
//		passGuardManager = PassGuardManager.getInstance(this);
//		passGuardManager.setWebView(mWebView);
//		mWebView.addJavascriptInterface(passGuardManager, "passGuardManager");
//		mWebView.addJavascriptInterface(WebActivity.this, "activity");
//	}
//
//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		if (passGuardManager.hasKeyBoardShowing()) {
//			passGuardManager.StopPassGuardKeyBoardAll();
//			return true;
//		}
//		finish();
//		passGuardManager.destory("selpwd");
//		return super.onKeyUp(keyCode, event);
//	}
}
