# Generated by Django 5.0.4 on 2024-05-03 19:14

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('task', '0003_alter_tag_title'),
    ]

    operations = [
        migrations.RenameField(
            model_name='task',
            old_name='tag',
            new_name='tags',
        ),
    ]
