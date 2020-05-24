/*
 * InboxPager, an android email client.
 * Copyright (C) 2016-2020  ITPROJECTS
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package net.inbox.visuals;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import net.inbox.InboxPager;
import net.inbox.R;
import net.inbox.server.Handler;

public class Dialogs {

    public static void dialog_simple(final String title, final String msg, final AppCompatActivity ct) {
        ct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                if (title == null) builder.setTitle(ct.getString(R.string.app_name));
                else builder.setTitle(title);
                builder.setMessage(msg);
                builder.setCancelable(true);
                builder.setPositiveButton(ct.getString(android.R.string.ok), null);
                make_text_selectable(builder.show());
            }
        });
    }

    public static void dialog_exception(final Exception e, final AppCompatActivity ct) {
        ct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                builder.setTitle(ct.getString(R.string.ex_title));
                String str = e.getMessage() + "\n\n";
                StackTraceElement[] stack = e.getStackTrace();
                for (int i = 0;i < e.getStackTrace().length;++i) {
                    str = str.concat(stack[i].getClassName() +":"+ stack[i].getLineNumber() + "\n");
                }
                builder.setMessage(str);
                builder.setCancelable(true);
                builder.setPositiveButton(ct.getString(android.R.string.ok), null);
                make_text_selectable(builder.show());
            }
        });
    }

    public static void dialog_view_message(final String msg, final AppCompatActivity ct) {
        ct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ct);
                builder.setTitle(ct.getString(R.string.menu_see_full_message_title));
                builder.setMessage(msg);
                builder.setCancelable(true);
                builder.setPositiveButton(ct.getString(android.R.string.ok), null);
                make_text_selectable(builder.show());
            }
        });
    }

    public static void dialog_view_log(AppCompatActivity ct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle(ct.getString(R.string.menu_log));
        builder.setMessage(InboxPager.log);
        builder.setCancelable(true);
        builder.setPositiveButton(ct.getString(android.R.string.ok), null);
        builder.setNeutralButton(ct.getString(R.string.btn_log_clear),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        InboxPager.log = " ";
                    }
                });
        builder.show();
        make_text_selectable(builder.show());
    }

    public static void dialog_view_ssl(boolean ssl_status, Handler handler, AppCompatActivity ct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);
        builder.setTitle(ct.getString(R.string.ssl_auth_popup_title));
        builder.setCancelable(true);
        builder.setPositiveButton(ct.getString(android.R.string.ok), null);
        if (ssl_status) {
            builder.setMessage(handler.get_last_connection_data());
        } else {
            builder.setMessage(ct.getString(R.string.ssl_auth_popup_bad_connection));
        }
        make_text_selectable(builder.show());
    }

    private static void make_text_selectable(AlertDialog adg) {
        TextView text_box = adg.findViewById(android.R.id.message);
        if (text_box != null) text_box.setTextIsSelectable(true);
    }

    public static void toaster(final boolean time, final String msg, final AppCompatActivity ct) {
        ct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (time) {
                    Toast.makeText(ct, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ct, msg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void toaster(final boolean time, final String msg, final Context ct) {
        if (time) {
            Toast.makeText(ct, msg, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ct, msg, Toast.LENGTH_LONG).show();
        }
    }
}