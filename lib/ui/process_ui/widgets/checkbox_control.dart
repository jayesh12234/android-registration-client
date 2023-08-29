import 'package:flutter/material.dart';
import 'package:flutter_screenutil/flutter_screenutil.dart';

import 'package:provider/provider.dart';
import 'package:registration_client/model/field.dart';
import 'package:registration_client/pigeon/biometrics_pigeon.dart';
import 'package:registration_client/pigeon/demographics_data_pigeon.dart';
import 'package:registration_client/provider/global_provider.dart';
import 'package:registration_client/provider/registration_task_provider.dart';
import 'package:registration_client/utils/app_config.dart';

class CheckboxControl extends StatelessWidget {
  const CheckboxControl({super.key, required this.field});
  final Field field;

  @override
  Widget build(BuildContext context) {
    return Card(
      color: pure_white,
      margin: EdgeInsets.fromLTRB(16, 8, 16, 8),
      child: Padding(
        padding: const EdgeInsets.fromLTRB(12, 14, 12, 14),
        child: Row(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            SizedBox(
                height: 20,
                width: 20,
                child: Checkbox(
                    activeColor: solid_primary,
                    value: (context
                            .watch<GlobalProvider>()
                            .fieldInputValue
                            .containsKey(field.id))
                        ? context
                            .watch<GlobalProvider>()
                            .fieldInputValue[field.id]
                        : false,
                    onChanged: (value) async{
                      context.read<GlobalProvider>().setInputMapValue(
                          field.id!,
                          value,
                          context.read<GlobalProvider>().fieldInputValue);
                      context
                          .read<RegistrationTaskProvider>()
                          .addConsentField(value != null && value ? 'Y' : 'N');
                          await DemographicsApi().addDemographicField(field.id!, value!.toString());
                    })),
            SizedBox(
              width: 8,
            ),
            Flexible(
              child: (field.inputRequired!)
                  ? RichText(
                      text: TextSpan(
                      text: context
                          .read<GlobalProvider>()
                          .chooseLanguage(field.label!),
                          style: TextStyle(color: black_shade_1),
                      children: [
                        TextSpan(
                          text: " *",
                          style: TextStyle(color: Colors.red, fontSize: 14),
                        )
                      ],
                    ))
                  : Text(
                      context
                          .read<GlobalProvider>()
                          .chooseLanguage(field.label!),
                    ),
            ),
          ],
        ),
      ),
    );
  }
}