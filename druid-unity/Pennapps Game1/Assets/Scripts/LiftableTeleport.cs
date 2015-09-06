using UnityEngine;
using System.Collections;

[RequireComponent(typeof(Collider))]
public class LiftableTeleport : MonoBehaviour {
	private Vector3 startingPosition;
	public static int highlightedCnt = 0;
	public static int gameLevel = 1;
	public static float elevationScaleFactor = 1.0f;
	private float bigPitch = -0.1f; //dummy (not used)

	const float levelStep = 2.0f; //distance traversed to clear each level
	const int numberOfLevels = 4; //currently. Can be upgraded later
	const float maxElevation = 80.0f; //angle in degrees
	
	void Start() {
		startingPosition = transform.localPosition;
		SetGazedAt(false);
	}

	public void SetGazedAt(bool gazedAt) {
		if (gazedAt && highlightedCnt == 0 && GetComponent<Renderer>().material.color != Color.black) {
			GetComponent<Renderer>().material.color = Color.green;
			highlightedCnt++;
		}		
	}

	void FixedUpdate () {
		float jumpHeight = 0.0f;
		if (GetComponent<Renderer>().material.color == Color.green) {			
			AndroidJavaClass cls_UnityPlayer = new AndroidJavaClass ("com.unity3d.player.UnityPlayer");			
			AndroidJavaObject obj_Activity = cls_UnityPlayer.GetStatic<AndroidJavaObject>("currentActivity");			

			string pitch = obj_Activity.CallStatic<string>("getPitch");

			if(!int.TryParse(obj_Activity.CallStatic<string>("getLevel"), out gameLevel))	{
				gameLevel = 1;
			}

			if(!float.TryParse(obj_Activity.CallStatic<string>("getScaleFactor"), out elevationScaleFactor))	{
				elevationScaleFactor = 1.0f;
			}

//			if(!(int.TryParse("2", out gameLevel)))	{
//				gameLevel = 1;
//			}
//			
//			if(!float.TryParse("0.0", out elevationScaleFactor))	{
//				elevationScaleFactor = 1.0f;
//			}
//
//			bigPitch = bigPitch + 0.01f;
//			string pitch = bigPitch.ToString();

			if(float.Parse(pitch) > 0)	{				
				jumpHeight = (float.Parse(pitch) - transform.localPosition.y) * (levelStep * elevationScaleFactor * numberOfLevels)/maxElevation;
				transform.Translate(0.0f, jumpHeight, 0.0f, Space.World);
			}

		}
		
		if(transform.localPosition.y >= levelStep * gameLevel)	{
//			gameLevel++;
			GetComponent<Renderer>().material.color = Color.black;
			transform.localPosition = new Vector3(transform.localPosition.x, transform.localPosition.y, -3.0f);
			highlightedCnt = 0;
			return;
		}
	}
}
